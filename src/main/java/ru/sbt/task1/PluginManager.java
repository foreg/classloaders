package ru.sbt.task1;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Ваша задача написать загрузчик плагинов в вашу систему. Допустим вы пишите свой браузер и хотите,
 * чтобы люди имели имели возможность писать плагины для него.
 * Соответственно, разные разработчики могут назвать свои классы одинаковым именем,
 * ваш загрузчик должен корректно это обрабатывать.
 *
 * @see ru.sbt.task1.Plugin - это базовый интерфейс  для всех плагинов
 * <p>
 * PluginManager ищет скомпилированные классы плагина в папке pluginRootDirectory\pluginName\
 */
public class PluginManager {
    private final String pluginRootDirectory;
    public PluginManager( String pluginRootDirectory ) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load( String pluginName, String pluginClassName ) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(pluginRootDirectory  + pluginName )});
        return (Plugin) urlClassLoader.loadClass(pluginClassName).newInstance();
    }
}
