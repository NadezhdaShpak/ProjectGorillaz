package com.javarush.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class ImageService {
    private static final String IMAGES_FOLDER = "images";
    private static final String PART_NAME = "image";
    private static final String NO_IMAGE = "no-image.webp";
    private static final List<String> EXTENSIONS = List.of(
            ".jpeg", ".jpg", ".png", ".gif", ".bmp", ".webp");

    public final Path WEB_INF = Paths.get(URI.create(
                    Objects.requireNonNull(
                            ImageService.class.getResource("/")
                    ).toString()))
            .getParent();

    private final Path imagesFolder = WEB_INF.resolve(IMAGES_FOLDER);

    @SneakyThrows
    public ImageService() {
        Files.createDirectories(imagesFolder);
    }

    public void uploadImage(HttpServletRequest req, String imageID) throws IOException, ServletException {
        Part data = req.getPart(PART_NAME);
        if (Objects.nonNull(data) && data.getInputStream().available() > 0) {
            String filename = data.getSubmittedFileName();
            String extension = filename.substring(filename.lastIndexOf("."));
            deleteOldFiles(imageID);
            filename = imageID + extension;
            uploadImageInternal(filename, data.getInputStream());
        }
    }

    @SneakyThrows
    private void uploadImageInternal(String filename, InputStream inputStream) {
    try (inputStream){
        if (inputStream.available() > 0) {
            Files.copy(inputStream, imagesFolder.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    }

    private void deleteOldFiles(String imageID) {
        EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(imageID + ext))
                .filter(Files::exists)
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    @SneakyThrows
    public Path getImagePath(String filename) {
        return EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(filename + ext))
                .filter(Files::exists)
                .findAny()
                .orElse(imagesFolder.resolve(NO_IMAGE));
    }

}
