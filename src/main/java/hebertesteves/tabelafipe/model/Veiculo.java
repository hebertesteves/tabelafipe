package hebertesteves.tabelafipe.model;

public class Veiculo {
    private String valor;
    private String marca;
    private String modelo;
    private Integer ano;
    private String combustivel;

    public Veiculo() {
    }

    public Veiculo(DadosVeiculo dadosVeiculo) {
        this.valor = dadosVeiculo.valor();
        this.marca = dadosVeiculo.marca();
        this.modelo = dadosVeiculo.modelo();
        this.ano = dadosVeiculo.ano();
        this.combustivel = dadosVeiculo.combustivel();
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    @Override
    public String toString() {
        return "Veiculo[" +
                "valor=" + valor +
                ", marca=" + marca +
                ", modelo=" + modelo +
                ", ano=" + ano +
                ", combustivel=" + combustivel +
                "]";
    }
}
