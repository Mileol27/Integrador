package clases;

import java.util.Date;

public class articulo {
    private int id;
    private String descripcion;
    private Date creado_el;
    private usuario creado_por;
    private String marca;
    private String modelo;
    private String num_serie;
    private categoria categoria;
    private Date f_modiciacion;
    private String observaciones;
    private estado estado;
 
    
    //Constructor
    public articulo(int id, String descripcion, Date creado_el, usuario creado_por, String marca, String modelo, String num_serie, categoria categoria, Date f_modiciacion, String observaciones, estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.creado_el = creado_el;
        this.creado_por = creado_por;
        this.marca = marca;
        this.modelo = modelo;
        this.num_serie = num_serie;
        this.categoria = categoria;
        this.f_modiciacion = f_modiciacion;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    
    //Metodos
    public int contar_articulos_total(){
        return 0;
    }
    
    public int contar_por_status(String status){
        return 0;
    }
    
    public void eliminar() {
    }

    public void guardar() {

    }
    
    
    
    
    /**
    //Encapsulamiento
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the creado_el
     */
    public Date getCreado_el() {
        return creado_el;
    }

    /**
     * @param creado_el the creado_el to set
     */
    public void setCreado_el(Date creado_el) {
        this.creado_el = creado_el;
    }

    /**
     * @return the creado_por
     */
    public usuario getCreado_por() {
        return creado_por;
    }

    /**
     * @param creado_por the creado_por to set
     */
    public void setCreado_por(usuario creado_por) {
        this.creado_por = creado_por;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the num_serie
     */
    public String getNum_serie() {
        return num_serie;
    }

    /**
     * @param num_serie the num_serie to set
     */
    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }

    /**
     * @return the categoria
     */
    public categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the f_modiciacion
     */
    public Date getF_modiciacion() {
        return f_modiciacion;
    }

    /**
     * @param f_modiciacion the f_modiciacion to set
     */
    public void setF_modiciacion(Date f_modiciacion) {
        this.f_modiciacion = f_modiciacion;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the estado
     */
    public estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(estado estado) {
        this.estado = estado;
    }
    
    
    
}
