package com.Musicom.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static class PageNotFoundException extends NotFoundException {
        public PageNotFoundException(int page) {
            super("Page " + page + " not found");
        }
    }

    public static class BandNotFoundException extends NotFoundException {
        public BandNotFoundException(String name) {
            super("Band with name " + name + " not found");
        }
        public BandNotFoundException(long id) {
            super("Band with id " + id + " not found");
        }
    }

    public static class AlbumNotFoundException extends NotFoundException {
        public AlbumNotFoundException(String name) {
            super("Album with name " + name + " not found");
        }
    }

    public static class TrackNotFoundException extends NotFoundException {
        public TrackNotFoundException(String name) {
            super("Track with name " + name + " not found");
        }
    }
}
