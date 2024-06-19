package com.example.shoppe_project.modal.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


@Data
@MappedSuperclass // ĐỂ ĐÁNH DẤU CLASS NÀY CŨNG LÀ 1 PHẦN TRONG ENTITY
public class BaseEntity {

    @Column(name = "create_date")
    protected Date create_date;

    @Column(name = "maker")
    protected String maker;

    @Column(name = "daysUpdate")
    protected Date daysUpdate;

    @Column(name = "makerUpdate")
    protected String makerUpdate;


    /**
     * Hàm này được gọi khi entity được thêm mới
     */
    @PrePersist
    public void onPrepersist(){
        this.create_date = new Date();
        this.maker = "MR.A";
    }

    /**
     * Hàm này được gọi khi entity được update
     */
    @PreUpdate
    public void onPreUpdate(){
        this.daysUpdate = new Date();
        this.makerUpdate = "MR.B";
    }


    // VÍ DỤ
    /**
     *  Dùng để thực hiện phép cộng
     * @param a: số thứ nhất
     *
     * @param b: số thứ hai
     * @return:  trả về tổng A và B
     */
    public int add(int a, int b){
        return a+ b;
    }
}
