package it.florentino.dark.timeplanapp.utils.enumaration;

public enum ShiftSlots {

    SLOT_1("8:00 - 9:00"),
    SLOT_2("9:00 - 10:00"),
    SLOT_3("10:00 - 11:00"),
    SLOT_4("11:00 - 12:00"),
    SLOT_5("12:00 - 13:00"),
    SLOT_6("14:00 - 15:00"),
    SLOT_7("15:00 - 16:00"),
    SLOT_8("16:00 - 17:00"),
    SLOT_9("17:00 - 18:00");

    private final String id;

    ShiftSlots(String id) { this.id = id; }

    public static ShiftSlots fromString(String id){

        for(ShiftSlots type : values()){
            if(type.getId().equals(id)){
                return type;
            }
        }
        return null;
    }

    public String getId() { return this.id; }
}
