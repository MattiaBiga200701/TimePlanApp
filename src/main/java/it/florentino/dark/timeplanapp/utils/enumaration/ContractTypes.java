package it.florentino.dark.timeplanapp.utils.enumaration;

public enum ContractTypes {

    PART_TIME("part-time"),
    FULL_TIME("full-time");
    private final String id;

    ContractTypes(String id){
        this.id = id;
    }

    public static ContractTypes fromString(String id){

        for(ContractTypes type : values()){
            if(type.getId().equals(id)){
                return type;
            }
        }
        return null;
    }

    public String getId(){ return this.id; }


}
