package com.example.graduationProject.DAO;

import com.example.graduationProject.entities.Images;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.hibernate.cfg.Configuration;

public class ImageDAO {
    private static final Logger log = LoggerFactory.getLogger(ImageDAO.class);
    Configuration configuration = new Configuration().configure();

    public void saveImage(Images images){
        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.save(images);
            log.info("Add Image name " + images.getFileName().toString());
            session.getTransaction().commit();
        }catch (Exception e){
            log.error(" Error add Image "+ e.getStackTrace());
        }
    }

    public Images getImageById(int idImage){
        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            Images image = session.get(Images.class, idImage);
            log.info("Get Image name: " + image.getFileName().toString());
            return image;

        }catch (Exception e){
            log.error(" Error Get Image "+ e.getStackTrace());
            return null;
        }
    }

    public Images getImageByFileName(String FileName){
        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            Query<Images> query = session.createQuery("FROM Images WHERE file_name = :fileName", Images.class);
            query.setParameter("fileName", FileName);

            // Выполняем запрос и получаем результат
            Images image = query.uniqueResult(); // uniqueResult() возвращает один объект или null, если не найдено
            if (image != null) {
                log.info("getImageByFileName name: " + image.getFileName());
            } else {
                log.info("Image not found with file name: " + FileName);
            }
            return image;
        }catch (Exception e){
            log.error(" Error getImageByFileName "+ e.getStackTrace());
            return null;
        }
    }

    public void deleteImage(int idImage){
        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            session.beginTransaction();
            Images image = getImageById(idImage);
            if (image != null) {
                session.delete(image); // удаляем изображение
                log.info("Deleted Image with ID: " + idImage);
                session.getTransaction().commit();
            } else {
                log.info("Image not found with ID: " + idImage);
            }
        }catch (Exception e){
            log.error(" Error add Image "+ e.getStackTrace());
        }
    }

    public void updateImage(Images image){
        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.update(image);
            session.getTransaction().commit();
            log.info("Updated Image with ID: " + image.getIdImage());
        }catch (Exception e){
            log.error(" Error Updated Image "+ e.getStackTrace());
        }
    }

    // возвращает список изображений по айди альбома
    public void getImagesByAlbumId(){

    }
}
