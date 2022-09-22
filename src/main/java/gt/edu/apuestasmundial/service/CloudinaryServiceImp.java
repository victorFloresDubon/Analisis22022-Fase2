package gt.edu.apuestasmundial.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImp implements CloudinaryService{

    Cloudinary cloudinary;

    private CloudinaryProperties properties;
    private Map<String, Object> valuesMap = new HashMap<>();

    public CloudinaryServiceImp(CloudinaryProperties properties){
        this.properties = properties;
        valuesMap.put("cloud_name", this.properties.getCloudName());
        valuesMap.put("api_key", this.properties.getApiKey());
        valuesMap.put("api_secret", this.properties.getApiSecret());
        valuesMap.put("secure", true);
        cloudinary = new Cloudinary(valuesMap);
    }

    @Override
    public Map upload(MultipartFile multipartFile){
        try {
            File file = convertir(multipartFile);
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();
            return result;
        } catch (IOException e){
            return null;
        }
    }

    @Override
    public Map delete(String id){
        try{
            Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
            return result;
        }catch (Exception e){
            return null;
        }
    }

    private File convertir(MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (IOException e){
            return null;
        }
        return file;
    }
}
