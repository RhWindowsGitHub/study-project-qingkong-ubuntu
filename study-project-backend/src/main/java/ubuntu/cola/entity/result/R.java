package ubuntu.cola.entity.result;

import lombok.Data;

/**
 * @author Cola_Ubuntu
 * @name R
 * @DATE 2023/4/27 下午12:44
 * @description 实体集 R
 */
@Data
public class R<T> {
    private Integer status;
    private String message;
    private Boolean success;
    private T data;

    private R(){}

    public static <T> R<T> build(T data,ResultEnum resultEnum,Boolean isSuccess){
        R<T> result = new R<>();
        if(data != null){
            result.setData(data);
        }
        result.setStatus(resultEnum.getStatus());
        result.setMessage(resultEnum.getMessage());
        result.setSuccess(isSuccess);
        return result;
    }

    public static <T> R<T> success(ResultEnum resultEnum){
        return build(null, resultEnum,true);
    }
    public static <T> R<T> success(T data,ResultEnum resultEnum){
        return build(data, resultEnum,true);
    }

    public static <T> R<T> fail(ResultEnum resultEnum){
        return build(null,resultEnum,false);
    }

    public static <T> R<T> fail(T data,ResultEnum resultEnum){
        return build(data,resultEnum,false);
    }


    public R<T> msg(String msg){
        this.setMessage(msg);
        return this;
    }
    public R<T> code(Integer code){
        this.setStatus(code);
        return this;
    }
}
