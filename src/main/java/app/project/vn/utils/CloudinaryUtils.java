package app.project.vn.utils;

import lombok.experimental.UtilityClass;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;

@UtilityClass
public class CloudinaryUtils {
    public String uploadImage(Object file) throws IOException {
        var config = new HashMap<>();
        config.put("cloud_name", "dq5r57nci");
        config.put("api_key", "753315133512146");
        config.put("api_secret", "JZFghsyNv9vhPmDTN7nlHpsN8ic");
        Cloudinary cloudinary = new Cloudinary(config);
        var uploadResult = cloudinary
                .uploader()
                .upload(file, ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }

}
