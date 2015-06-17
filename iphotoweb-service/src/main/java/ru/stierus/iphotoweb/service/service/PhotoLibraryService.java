package ru.stierus.iphotoweb.service.service;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class PhotoLibraryService {

    private final AtomicReference<PhotoLibrary> library = new AtomicReference<>();
    private final CopyOnWriteArrayList<PhotoLibraryListener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(PhotoLibraryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PhotoLibraryListener listener) {
        listeners.remove(listener);
    }

    private void triggerLibraryChanged(PhotoLibrary library) {
        listeners.forEach(listener -> {
            try{
                listener.onChange(library);
            }catch (Throwable e){
                // @todo add logging with slf4j
            }
        });
    }

    public synchronized void updateLibrary(PhotoLibrary newLibrary){
        library.set(newLibrary);
        triggerLibraryChanged(newLibrary);
    }

    public PhotoLibrary getLibrary(){
        return library.get();
    }
}
