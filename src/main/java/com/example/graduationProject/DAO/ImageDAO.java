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
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.hibernate.cfg.Configuration;

public class ImageDAO {
    private static final Logger log = LoggerFactory.getLogger(ImageDAO.class);

    public void saveImage(Images images){
        try(var sessionFactory = new Configuration().configure().buildSessionFactory();
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
        try(var sessionFactory = new Configuration().configure().buildSessionFactory();
            var session = sessionFactory.openSession();) {
            Query<Images> query = session.createQuery("FROM Images WHERE id_image = :fileName", Images.class);
            query.setParameter("fileName", idImage);

            // Выполняем запрос и получаем результат
            Images image = query.uniqueResult();
            if (image != null) {
                // Логируем имя файла, если изображение найдено
                log.info("Get Image name: " + image.getFileName());
                return image;
            } else {
                // Логируем, если изображение не найдено
                log.warn("Image with ID " + idImage + " not found.");
                return null;
            }

        }catch (Exception e){
            log.error(" Error Get Image by ID "+ e.getStackTrace());
            return null;
        }
    }

    public Images getImageByFileName(String FileName){
        try(var sessionFactory = new Configuration().configure().buildSessionFactory();
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
        try(var sessionFactory = new Configuration().configure().buildSessionFactory();
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
        try(var sessionFactory = new Configuration().configure().buildSessionFactory();
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
    public List<Images> getImagesByAlbumId(int idAlbum) {
        try (var sessionFactory = new Configuration().configure().buildSessionFactory();
             var session = sessionFactory.openSession()) {

            Query<Images> query = session.createQuery("FROM Images WHERE id_album = :idAlbum", Images.class);
            query.setParameter("idAlbum", idAlbum);

            List<Images> imagesList = query.list();

            // Логирование результатов
            if (imagesList.isEmpty()) {
                log.info("No images found for album ID: " + idAlbum);
            } else {
                log.info("Found " + imagesList.size() + " images for album ID: " + idAlbum);
                for (Images image : imagesList) {
                    log.info("Image found: " + image.getFileName());
                }
            }

            return imagesList;

        } catch (Exception e) {
            log.error("Error fetching images by album ID: " + e.toString(), e);
            return null;
        }
    }

}
