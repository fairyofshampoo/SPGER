package mx.uv.fei.sspger.logic;


public class Lgac {
    private String id;
    private String name;
    private String description;
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    @Override
    public String toString(){
        return id;
    }
}
