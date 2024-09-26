package Model;

public class Conversion {
    private String monedaBase;
    private String monedaDeseada;
    private Double monto;
    private Double resultado;
    private String fecha;

    public Conversion(String monedaBase, String monedaDeseada, Double monto, Double resultado, String fecha) {
        this.monedaBase = monedaBase;
        this.monedaDeseada = monedaDeseada;
        this.monto = monto;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public Conversion(ConversionRecord conversionRecord) {
        this.monedaBase = conversionRecord.base_code();
        this.monedaDeseada = conversionRecord.target_code();
        this.resultado = Double.valueOf(conversionRecord.conversion_result());
    }

    public String getMonedaBase() {
        return monedaBase;
    }

    public void setMonedaBase(String monedaBase) {
        this.monedaBase = monedaBase;
    }

    public String getMonedaDeseada() {
        return monedaDeseada;
    }

    public void setMonedaDeseada(String monedaDeseada) {
        this.monedaDeseada = monedaDeseada;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "monto = " + monto + " " + monedaBase + ", " +
                "resultado = " + resultado + " " + monedaDeseada + ", " +
                "fecha = " + fecha;
    }
}
