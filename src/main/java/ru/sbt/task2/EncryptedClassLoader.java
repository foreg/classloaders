package ru.sbt.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Данный класслоадер умеет загружать классы из файлов дешифрую их. Ваша задача переопределить метод findClass().
 * В нем лоадер считывает зашифрованный массив байт, дешифрует его и превращает в класс (с помощью метода defineClass).
 * <p>
 * На вход класслодер принимает ключ шифрования, рутовую папку, в которой будет искать классы, родительский класслодер.
 * Логика шифрования/дешифрования с использованием ключа может быть любой на ваш вкус
 * (например, каждый считаный байт класса увеличить на определение число).
 */
public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader( String key, File dir, ClassLoader parent ) {
        super( parent );
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        int offset = 0;
        for (char c : key.toCharArray()) {
            offset += c;
        }
        offset %= 255;
        try {
            byte[] bytes = Files.readAllBytes(dir.toPath());
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)((bytes[i] + offset) % 255);
            }
            return defineClass(name, bytes, 0, bytes.length);
        }
        catch ( IOException e ) {
            e.printStackTrace(System.out);
        }
        return super.findClass(name);
    }
}

