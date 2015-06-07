package br.com.viajabessa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabrielbernardopereira on 3/14/15.
 */
public class Pacote implements Parcelable {

    private int id;
    private String nome;
    private Double valor;
    private String foto;
    private String foto_thumb;
    private String descricao;

    public Pacote() {
    }

    /*
     * Parcelable Methods
     *
     */
    protected Pacote(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        valor = in.readDouble();
        foto = in.readString();
        foto_thumb = in.readString();
        descricao = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeDouble(valor);
        dest.writeString(foto);
        dest.writeString(foto_thumb);
        dest.writeString(descricao);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pacote> CREATOR = new Parcelable.Creator<Pacote>() {
        @Override
        public Pacote createFromParcel(Parcel in) {
            return new Pacote(in);
        }

        @Override
        public Pacote[] newArray(int size) {
            return new Pacote[size];
        }
    };

    /*
     * Getters and Setters
     *
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto_thumb() {
        return foto_thumb;
    }

    public void setFoto_thumb(String foto_thumb) {
        this.foto_thumb = foto_thumb;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}