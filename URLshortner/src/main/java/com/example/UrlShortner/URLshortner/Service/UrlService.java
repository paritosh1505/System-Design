package com.example.UrlShortner.URLshortner.Service;

import com.example.UrlShortner.URLshortner.Models.UrlEntry;
import com.example.UrlShortner.URLshortner.Repository.UrlRepo;
import com.example.UrlShortner.URLshortner.ServiceImple.Srviceimple;
import com.example.UrlShortner.URLshortner.Utils.EncryptUrl;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService implements Srviceimple {
    private final UrlRepo repo;
    @Autowired
    @Qualifier("redisTemp")
    private RedisTemplate<String,String> redisCheck;
    public UrlService(UrlRepo urepo){
        this.repo = urepo;
    }

    @Override
    public UrlEntry getOriginalUrlFromRedis(String ShortUrl){
        String originalUrl = redisCheck.opsForValue().get(ShortUrl);
        UrlEntry urlData = new UrlEntry();
        if(originalUrl!=null){
            urlData.setOriginalurl(redisCheck.opsForValue().get(ShortUrl));
            return urlData;//redis cache hit;
        }
        Optional<UrlEntry> urlval= Optional.ofNullable(GetOriginalDb(ShortUrl));
        if(urlval.isPresent()){
            String urlOriginal = urlval.get().getOriginalurl();
            redisCheck.opsForValue().set(ShortUrl,urlOriginal,60, TimeUnit.SECONDS);
            urlData.setOriginalurl(redisCheck.opsForValue().get(ShortUrl));
            return urlData;
        }
        return null;
    }

    @Override
    public UrlEntry UrlEntrytoDb(String urlvalue) throws InvalidKeyException {
        EncryptUrl encrypt = new EncryptUrl();
        String EncryptedUrl = encrypt.EncryptUrlWithGivenInput(urlvalue);
        String[] codeSplit = EncryptedUrl.split("/");
        String codeval = codeSplit[codeSplit.length-1];
        boolean isvalidUrl = CheckValidUrl(urlvalue);
        if(isvalidUrl){
            UrlEntry uentry = new UrlEntry();
            uentry.setOriginalurl(urlvalue);
            uentry.setEncryptedurl(EncryptedUrl);
            uentry.setEncodedentry(codeval);
            uentry.setCreateat(LocalDateTime.now());
            repo.save(uentry);
            return uentry;
        }
        return null;


    }

    @Override
    @Transactional
    public UrlEntry GetOriginalDb(String decodedUrl) {
        String[] splitval = decodedUrl.split("/");
        String decodeVal = splitval[splitval.length-1];
        UrlEntry entry = repo.FetchOriginalUrl(decodeVal);
        if(entry!=null){
            entry.setUpdatedat(LocalDateTime.now());
            int newClick = entry.getClickcount()-1;
            entry.setClickcount(newClick);
            repo.save(entry);
            Duration duration = Duration.between(entry.getCreateat(),entry.getUpdatedat());
            long totalSec= duration.getSeconds();
            System.out.println("new click cnt is"+newClick);
            System.out.println("total Second is  cnt is"+totalSec);

            if(newClick<=0 || totalSec > 900 ){
                try{
                    repo.RemoveEntryFromDb(decodeVal);
                }
                catch(Exception e){
                    System.out.println("error while removinf entry "+e.getMessage());
                }

            }
            return entry;
        }
        return null;
    }

    public boolean CheckValidUrl(String url){
        UrlValidator urlCheck = new UrlValidator(
                new String[]{"https","http","in","com","www","org"}
        );
       if(!urlCheck.isValid(url)){
           return false;
       }
       return url.contains("www") && url.contains("com");
    }
}
