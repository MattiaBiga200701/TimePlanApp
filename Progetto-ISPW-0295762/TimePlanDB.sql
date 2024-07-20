-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema timePlanDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema timePlanDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `timePlanDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `timePlanDB` ;

-- -----------------------------------------------------
-- Table `timePlanDB`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timePlanDB`.`Employee` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `contract_type` ENUM('part-time', 'full-time') NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `manager_id` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `timePlanDB`.`Notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timePlanDB`.`Notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(128) NOT NULL,
  `sender_role` ENUM('manager', 'employee') NOT NULL,
  `sender_manager_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `timePlanDB`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timePlanDB`.`Users` (
  `Username` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Role` VARCHAR(45) NOT NULL,
  `manager_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`Email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `timePlanDB`.`WorkShift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timePlanDB`.`WorkShift` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `shiftDate` DATE NOT NULL,
  `shiftTime` VARCHAR(45) NOT NULL,
  `employeeName` VARCHAR(45) NOT NULL,
  `employeeSurname` VARCHAR(45) NOT NULL,
  `employeeContract` ENUM('part-time', 'full-time') NOT NULL,
  `employeeEmail` VARCHAR(45) NOT NULL,
  `managerID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `unique_value` (`shiftDate` ASC, `shiftTime` ASC, `managerID` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `timePlanDB` ;

-- -----------------------------------------------------
-- procedure check_email
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_email`(IN var_email varchar(45), OUT var_count int)
BEGIN

   declare var_check_count int;

   SELECT COUNT(*) INTO var_check_count
   FROM Users
   WHERE Email = var_email;

   SET var_count = var_check_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure delete_work_shift
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_work_shift`(IN var_date date, IN var_time varchar(45),
                                                         IN var_name varchar(45), IN var_surname varchar(45),
                                                         IN var_contract varchar(45), IN var_email varchar(45),
                                                         IN var_manager_id int)
BEGIN

    DELETE FROM WorkShift
        WHERE shiftDate = var_date AND shiftTime = var_time AND employeeName = var_name
                AND employeeSurname = var_surname AND employeeContract = var_contract AND employeeEmail = var_email AND managerID = var_manager_id;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure employee_insertion
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `employee_insertion`(IN var_name varchar(45), IN var_surname varchar(45),
                                                          IN var_contract varchar(45), IN var_email varchar(45),
                                                          IN var_manager_id int, OUT var_status int)
BEGIN

    declare email_count int;

    SELECT COUNT(*) INTO email_count
    FROM Employee
    WHERE email = var_email;

    if email_count > 0 then
       set var_status = 1;
    else
        INSERT INTO `Employee` (`name`, `surname`, `contract_type`, `email`, `manager_id`)
            VALUES(var_name, var_surname, var_contract, var_email, var_manager_id);
        set var_status = 0;
    end if;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure generate_manager_id
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_manager_id`(OUT var_manager_id int)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE var_check_count INT;
    DECLARE var_id INT;

    -- Ripeti fino a quando non trovi un ID unico
    REPEAT
        -- Genera un numero casuale tra 100000 e 999999
        SET var_id = FLOOR(100000 + RAND() * 900000);

        -- Controlla se il numero generato è unico
        SELECT COUNT(*) INTO var_check_count 
        FROM `Users` 
        WHERE `manager_id` = var_id AND `Role` = 'manager';

        if var_check_count = 0 then
			set done = true;
        else
			set done = false;
		end if;

    UNTIL done
    END REPEAT;

    -- Restituisci l'ID unico generato
    SET var_manager_id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_notification
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_notification`(IN var_message varchar(128), IN var_role int, IN var_manager_id int)
BEGIN

    declare var_sender_role enum('manager', 'employee');

    if var_role = 1 then
        set var_sender_role = 'manager';
    else
        set var_sender_role = 'employee';
    end if;

    INSERT INTO Notification(message, sender_role, sender_manager_id)
        VALUES(var_message, var_sender_role, var_manager_id);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure isEmployeeRegistered
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `isEmployeeRegistered`(IN var_email varchar(45), IN var_manager_id int,
                                                            OUT var_check_count int)
BEGIN
    declare check_count int;

    SELECT COUNT(*) INTO check_count
    FROM Users
    WHERE Email = var_email AND manager_id = var_manager_id AND Role = 'employee';

    SET var_check_count = check_count;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure isShiftAssigned
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `isShiftAssigned`( IN var_date DATE, IN var_time varchar(45),  IN var_manager_id int, OUT var_check int)
BEGIN

    declare var_count int;

    SELECT COUNT(*) INTO var_count
    FROM WorkShift
    WHERE WorkShift.shiftDate = var_date AND shiftTime = var_time
            AND managerID = var_manager_id;

   SET var_check = var_count;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(IN var_email varchar(45), IN var_pass varchar(45), OUT var_role int,
                                             OUT var_username varchar(45), OUT var_manager_id int)
BEGIN
	declare var_user_role ENUM('manager', 'employee');
    
    select `Role`, `Username`, `manager_id` from `Users`
		where `Email` = var_email
        and `Password` = md5(var_pass)
        into var_user_role, var_username, var_manager_id;
        
	-- See the corresponding enum in the client
		if var_user_role = 'manager' then
			set var_role = 1;
		elseif var_user_role = 'employee' then
			set var_role = 2;
		else
			set var_role = 3;
		end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure managerAssociated
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `managerAssociated`(IN var_manager_id int, OUT var_username varchar(45), 
																OUT var_email varchar(45), OUT var_password varchar(45),
                                                                OUT var_role int, OUT var_manager_id_associated int)
BEGIN
	declare var_user_role ENUM('manager', 'employee');
    
	SELECT `Username`, `Email`, `Password`, `Role`, `manager_id`  FROM `Users`
	WHERE `manager_id` = var_manager_id
    AND `Role` = 'manager'
    INTO var_username, var_email, var_password, var_user_role, var_manager_id_associated;
    
    if var_user_role = 'manager' then
		set var_role = 1;
	elseif var_user_role = 'employee' then
		set var_role = 2;
	else
		set var_role = 3;
	end if;
    


END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure read_employee_list
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `read_employee_list`(IN var_manager_id int)
BEGIN

    SELECT Employee.`name`, Employee.`surname` , Employee.`contract_type`, Employee.`email`, Employee.`manager_id`
    FROM Employee
    WHERE Employee.`manager_id` = var_manager_id;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure read_notifications
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `read_notifications`(IN var_role int, IN var_manager_id int)
BEGIN
    
    

    if var_role = 1 then

        SELECT message, sender_role, sender_manager_id
        FROM Notification
        WHERE sender_manager_id = var_manager_id AND sender_role = 'employee';
        

    elseif var_role = 2 then

        SELECT message, sender_role, sender_manager_id
        FROM Notification
        WHERE sender_manager_id = var_manager_id AND sender_role = 'manager';


    end if;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure read_work_shift_list
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `read_work_shift_list`(IN var_date date, IN var_email varchar(45), IN var_role int,
                                                            IN var_manager_id int)
BEGIN


    if var_role = 1 then
        SELECT shiftTime, employeename, employeesurname, employeecontract, employeeEmail
        FROM WorkShift
        WHERE shiftDate = var_date AND managerID = var_manager_id;

    elseif var_role = 2 then

        SELECT shiftTime, employeename, employeesurname, employeecontract, employeeEmail
        FROM timePlanDB.WorkShift
        WHERE shiftDate = var_date
          AND shiftDate IN (
            SELECT shiftDate
            FROM WorkShift
            WHERE employeeEmail = var_email
              AND shiftDate = var_date
        );

    end if;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure registration
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `registration`(IN var_username varchar(45), IN var_email varchar(45),
                                                    IN var_password varchar(45), IN var_role int, IN var_manager_id int)
BEGIN
	declare var_user_role ENUM('manager', 'employee');
    
    if var_role = 1 then
		set var_user_role = 'manager';
	elseif var_role = 2 then
		set var_user_role = 'employee';
	end if;


	INSERT INTO `Users`(`Username`, `Email`, `Password`, `Role`, `manager_id`)
	VALUES (var_username, var_email, md5(var_password), var_user_role, var_manager_id);


END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure remove_employee
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_employee`(IN var_email varchar(45))
BEGIN

    DELETE FROM Employee
        WHERE email = var_email;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure shiftCount
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `shiftCount`(IN var_date date, IN var_name varchar(45), IN var_surname varchar(45),
                                                  IN var_contract varchar(45), IN var_email varchar(45),
                                                  IN var_manager_id int, OUT var_shift_count int)
BEGIN

    declare var_count int;

    SELECT COUNT(*) INTO var_count
    FROM WorkShift
    WHERE WorkShift.shiftDate = var_date AND employeeName = var_name AND employeeSurname = var_surname AND employeeContract = var_contract
            AND WorkShift.employeeEmail = var_email AND managerID = var_manager_id;

    SET var_shift_count = var_count;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure work_shift_insertion
-- -----------------------------------------------------

DELIMITER $$
USE `timePlanDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `work_shift_insertion`(IN var_date date, IN var_time varchar(45),
                                                            IN var_name varchar(45), IN var_surname varchar(45),
                                                            IN var_contract varchar(45), IN var_email varchar(45),
                                                            IN var_manager_id int)
BEGIN

    INSERT INTO `WorkShift`(`shiftDate`, `shiftTime`, `employeeName`, `employeeSurname`, `employeeContract`, `employeeEmail`, `managerID`)
        VALUES (var_date, var_time, var_name, var_surname, var_contract, var_email, var_manager_id);

END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
