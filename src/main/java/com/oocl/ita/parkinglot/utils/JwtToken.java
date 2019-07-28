package com.oocl.ita.parkinglot.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token令牌加密与解密
 * @author Gordon
 * @version 创建时间 ： 2019/7/28.
 */
public class JwtToken {
    /**
     * 密钥
     */
    private static  final String SECRET="xxxx";
    /**
     * 默认字段key:exp
     */
    private static final String EXP="exp";
    /**
     * 默认字段key:payload
     */
    private static final String PAYLOAD="payload";
    /**
     * 有效时间(一天)
     */
    private static final long TIME=86400000;
    //private static final long TIME=10000;
    /**
     * 加密
     * @param object 加密数据
     * @param <T> 加密对象
     * @return 加密后的字符串
     */
    public static <T> String encode(T object){
        try{
            final JWTSigner signer=new JWTSigner(SECRET);
            final Map<String ,Object> data=new HashMap<>(10);
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonString=objectMapper.writeValueAsString(object);
            data.put(PAYLOAD,jsonString);
            data.put(EXP,System.currentTimeMillis()+TIME);
            return signer.sign(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数据解密
     * @param jwt 解密数据
     * @param tClass 解密类型
     * @param <T> 解密字符串
     * @return 解密后的对象
     */
    public static <T> T decode(String jwt,Class<T> tClass){
        if(jwt==null){
            return null;
        }
        final JWTVerifier jwtVerifier=new JWTVerifier(SECRET);
        try{
            final Map<String,Object> data=jwtVerifier.verify(jwt);
            //判断数据是否超时或者符合标准
            if(data.containsKey(EXP)&&data.containsKey(PAYLOAD)){
                long exp= (long) data.get(EXP);
                long currentTimeMillis=System.currentTimeMillis();
                if(exp>currentTimeMillis){
                    String json= (String) data.get(PAYLOAD);
                    ObjectMapper objectMapper=new ObjectMapper();
                    return objectMapper.readValue(json,tClass);
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}