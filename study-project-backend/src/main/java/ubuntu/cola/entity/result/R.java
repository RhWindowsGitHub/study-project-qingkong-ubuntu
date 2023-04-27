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
    private Integer code;
    private String msg;
    private T data;

    private R(){}

    public static <T> R<T> build(T data,ResultEnum resultEnum){
        R<T> result = new R<>();
        if(data != null){
            result.setData(data);
        }
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static <T> R<T> success(ResultEnum resultEnum){
        return build(null, resultEnum);
    }
    public static <T> R<T> success(T data,ResultEnum resultEnum){
        return build(data, resultEnum);
    }

    public static <T> R<T> fail(ResultEnum resultEnum){
        return build(null,resultEnum);
    }

    public static <T> R<T> fail(T data,ResultEnum resultEnum){
        return build(data,resultEnum);
    }


    public R<T> msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public R<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
