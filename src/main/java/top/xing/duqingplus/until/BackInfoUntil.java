package top.xing.duqingplus.until;

import lombok.Data;

@Data
public class BackInfoUntil<T> {
    private T info;
    private String msg;
    private String code;


    public BackInfoUntil(String code,String msg){
        this.code = code;
        this.msg = msg;
        this.info = (T) "";
    }

   public BackInfoUntil(String code,String msg,T info){
        this.info = info;
        this.code = code;
        this.msg = msg;
   }

   public static BackInfoUntil<String> success(){
        return new BackInfoUntil<String>("200","操作成功","");
   }

   public static BackInfoUntil<String> fail(){
        return new BackInfoUntil<>("400","操作失败","");
   }
}
