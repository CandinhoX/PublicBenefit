package br.com.candinho.publicbenefit.model;

public class Model {

    private String name;
    private String ano;
    private String categorie;
    private String image_url;
    private String description;

    public Model(){

    }

    public Model(String name, String ano, String categorie, String image_url, String description ) {

        this.name = name;
        this.categorie = categorie;
        this.image_url = image_url;
        this.ano = ano;
        this.description = description;


    }

    public String getName() {
        return name;
    }

    public String getAno() {
        return ano;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
