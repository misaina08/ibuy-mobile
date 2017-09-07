package modele;

/**
 * Created by misa on 8/23/2017.
 */

public class ResultWS extends BaseModele {
    private Integer res;

    public ResultWS(){};
    public ResultWS(Integer res){
        this.res = res;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }
}
