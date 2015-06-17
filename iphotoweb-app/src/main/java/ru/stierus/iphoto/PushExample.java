package ru.stierus.iphoto;

import ru.stierus.iphotoweb.service.model.PhotoLibrary;
import ru.stierus.iphotoweb.service.service.PhotoLibraryListener;
import ru.stierus.iphotoweb.service.service.PhotoLibraryService;

import java.util.UUID;

public class PushExample implements PhotoLibraryListener {
    private final PhotoLibraryService photoLibraryService;
    private final PushService pushService;

    public PushExample(PhotoLibraryService photoLibraryService, PushService pushService) {
        this.photoLibraryService = photoLibraryService;
        this.pushService = pushService;
    }

    public void init() {
        photoLibraryService.addListener(this);
    }

    @Override
    public void onChange(PhotoLibrary library) {
        pushService.broadcast(library);
    }

    interface PushService {
        void broadcast(PhotoLibrary library);
        void unicast(PhotoLibrary library, UUID member);
    }
}
