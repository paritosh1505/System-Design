package com.example.UrlShortner.URLshortner.ClientSystem;

import com.example.UrlShortner.URLshortner.Models.UrlEntry;
import com.example.UrlShortner.URLshortner.ServiceImple.Srviceimple;
import com.example.UrlShortner.URLshortner.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;

@RestController
@RequestMapping("/api")
public class CleintReq {
    ApiResponse apirep = new ApiResponse();
    private  Srviceimple methodUrl;

    public CleintReq(Srviceimple met){
        this.methodUrl=met;
    }
    @PostMapping("/url")
    public ResponseEntity<ApiResponse> SendUrlToDb(@RequestParam String urlEntry) throws InvalidKeyException {
        UrlEntry isUrlEntered = methodUrl.UrlEntrytoDb (urlEntry);

        if(isUrlEntered!=null){
            apirep.setMessage("Url is valid and entered in DB");
            apirep.setStatus(true);
            apirep.setEncodedUrl(isUrlEntered.getEncryptedurl());
            return ResponseEntity.status(HttpStatus.OK).body(apirep);
        }
        else{
            apirep.setMessage("Url is not valid");
            apirep.setStatus(false);
            apirep.setEncodedUrl("Url cannot be Encrypted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apirep);
        }
    }
    @GetMapping("/geturl")
    public ResponseEntity<ApiResponse> getOriginalUrl(@RequestParam String encUrl){
        UrlEntry urlval = methodUrl.getOriginalUrlFromRedis(encUrl);
        if(urlval!=null){
            apirep.setMessage("Original Url is fetched from DB");
            apirep.setStatus(true);
            apirep.setEncodedUrl(urlval.getOriginalurl());
            return ResponseEntity.status(HttpStatus.OK).body(apirep);
        }
        else{
            apirep.setMessage("********Error:-Original Url is not Fetched************");
            apirep.setStatus(false);
            apirep.setEncodedUrl(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apirep);
        }
    }
}
