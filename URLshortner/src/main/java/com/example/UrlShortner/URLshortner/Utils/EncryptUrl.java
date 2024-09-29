package com.example.UrlShortner.URLshortner.Utils;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptUrl {
    private String Url;
    public String getEncryptedUrl(String url){
        return url;
    }
    public void setEncryptedUrl(String url){
        this.Url= url;
    }

    public EncryptUrl(){}
    public EncryptUrl(String url){
        this.Url = url;
    }
    public String EncryptUrlWithGivenInput(String url) throws InvalidKeyException {
        String regex = "\\bwww\\S+?\\.com\\b";
        Pattern patter = Pattern.compile(regex);
        Matcher matcher = patter.matcher(url);
        String convertUrl = matcher.replaceAll("PariShortUrl");
        String[] splitarr = convertUrl.split("/");
        String encrypt = splitarr[splitarr.length-1];
        ///System.out.println(encrypt);
        String ecryptedEndpoint = generateShortCode(encrypt);
        convertUrl = convertUrl.replace(encrypt,ecryptedEndpoint);

        return convertUrl;
    }
    public String generateShortCode(String urlVal){
        String allCOde = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ1234567890";
        int ShortCodeLen = 8;
        StringBuilder shortCode = new StringBuilder();
        for(int i=0;i<ShortCodeLen;i++){
            String pickRandom = String.valueOf(allCOde.charAt((int)(Math.random()*allCOde.length())));
            shortCode.append(pickRandom);
        }

        return String.valueOf(shortCode);
    }
   /* public String EncypturlEndpoint(String urlval) throws InvalidKeyException {
        try{
            String algo = "AES/CBC/PKCS5Padding";
            String secretKey = "myPersonSecretKe";
            Cipher cipher = Cipher.getInstance(algo);
            SecretKeySpec secSpecs = new SecretKeySpec(secretKey.getBytes(),"AES");
            //generate random IV so that for every exeuction it will be diff
            byte[] iv = new byte[16];
            SecureRandom randval = new SecureRandom();
            randval.nextBytes(iv);
            IvParameterSpec ivspecs = new IvParameterSpec(iv);
            //IvParameterSpec ivspecs = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.ENCRYPT_MODE,secSpecs,ivspecs);
            byte[] encryptedData = cipher.doFinal(urlval.getBytes());
            byte[] newArrayencryptedData = new byte[encryptedData.length+1+iv.length+1];
            System.out.println("**********" +encryptedData.length);
            System.out.println("**********" +iv.length);

            System.out.println("**********" +newArrayencryptedData.length);
            System.arraycopy(iv,0,newArrayencryptedData,0,iv.length);
            System.out.println("**********" + Arrays.toString(newArrayencryptedData));
            System.arraycopy(encryptedData,0,newArrayencryptedData,iv.length+1,encryptedData.length);
            return Base64.getUrlEncoder().encodeToString(newArrayencryptedData);
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e){
            System.out.println("Error while encryption"+e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }*/
}
