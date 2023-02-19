package com.chengfei.buyee.common.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 64)
    private String name;
    @Column(nullable = false, length = 3)
    private String symbol;
    @Column(nullable = false, length = 4)
    private String code;
    public Currency() {};
    public Currency(String name, String symbol, String code) {
	this.name = name;
	this.symbol = symbol;
	this.code = code;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
	return "Currency [id=" + id + ", name=" + name + ", symbol=" + symbol + ", code=" + code + "]";
    }
}
