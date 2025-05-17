import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonSaver {
    private final String path;
    private final ObjectMapper mapper;

    public JsonSaver(String path){
        this.path = path;
        this.mapper = new ObjectMapper();
    }

    public void save(AllData data){
        try{
            mapper.writeValue(new File(path), data);
        } catch (IOException e) {
            System.out.print("Error while saving: "+e.getMessage());
        }
    }

    public AllData read(){
        File file = new File(path);
        if(!file.exists()){return null;}

        try{
            return mapper.readValue(file, AllData.class);
        } catch (IOException e) {
            System.out.println("Error while reading: "+e.getMessage());
            return null;
        }
    }
}
