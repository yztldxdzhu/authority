package com.yhguo.common.encryption;

import com.yhguo.common.config.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class AesCoder {

    @Autowired
    GlobalConfig globalConfig;

    private ScriptEngine engine = null;

    public AesCoder() {
        ScriptEngineManager sem = new ScriptEngineManager();
        engine = sem.getEngineByName("javascript");     //python or jython,
        try {
            engine.eval(new InputStreamReader(AesCoder.class.getResourceAsStream("crypto-js.js")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String encrypt(String src) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        String key = globalConfig.getSecureKey();
        String iv = globalConfig.getSecureIv();
        String keyImpl = "var key = CryptoJS.enc.Utf8.parse('" + globalConfig.getSecureKey() + "');";
        String ivImpl = "var iv = CryptoJS.enc.Utf8.parse('" + globalConfig.getSecureIv() + "');";
        engine.eval(keyImpl);
        engine.eval(ivImpl);
        engine.eval(" function Encrypt(word) {" +
                "	    	  var srcs = CryptoJS.enc.Utf8.parse(word);" +
                "	    	  var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });" +
                "	    	  return encrypted.ciphertext.toString().toUpperCase();" +
                "	    	}");
        //取得调用接口
        Invocable jsInvoke = (Invocable) engine;
        //定义加法函数
        Object result = jsInvoke.invokeFunction("Encrypt", new Object[]{src});
        return result.toString();
    }


    public String decrypt(String target) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        String key = globalConfig.getSecureKey();
        String iv = globalConfig.getSecureIv();
        String keyImpl = "var key = CryptoJS.enc.Utf8.parse('" + globalConfig.getSecureKey() + "');";
        String ivImpl = "var iv = CryptoJS.enc.Utf8.parse('" + globalConfig.getSecureIv() + "');";
        engine.eval(keyImpl);
        engine.eval(ivImpl);
        engine.eval("function Decrypt (word) {var encryptedHexStr = CryptoJS.enc.Hex.parse(word);" +
                "  var srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr);" +
                "  var decrypt = CryptoJS.AES.decrypt(srcs, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });" +
                "  var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);" +
                "  return decryptedStr.toString();" +
                " }");
        //取得调用接口
        Invocable jsInvoke = (Invocable) engine;
        //定义加法函数
        Object result = jsInvoke.invokeFunction("Decrypt", new Object[]{target});
        return result.toString();
    }

}
