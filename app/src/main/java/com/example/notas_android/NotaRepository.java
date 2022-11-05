package com.example.notas_android;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notas_android.db.NotaRoomDataBase;
import com.example.notas_android.db.dao.NotaDao;
import com.example.notas_android.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application){
        NotaRoomDataBase db = NotaRoomDataBase.getDataBase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAll();
        allNotasFavoritas = notaDao.getAllFavoritas();

    }

    public LiveData<List<NotaEntity>> getAll() {return allNotas; }

    public LiveData<List<NotaEntity>> getAllFavs() {return allNotasFavoritas; }

    public void insert (NotaEntity nota){
        new insertAsyncTask(notaDao).execute(nota);

    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void>{
        private NotaDao notaDaoAsyncTask;

        insertAsyncTask(NotaDao dao){
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.insert(notaEntities[0]);

            return null;
        }
    }

    public void update(NotaEntity nota){new updateAsyncTask(notaDao).execute(nota);}

    private static class updateAsyncTask extends AsyncTask<NotaEntity, Void, Void>{
        private NotaDao notaDaoAsyncTask;

        updateAsyncTask(NotaDao dao){
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.update(notaEntities[0]);

            return null;
        }
    }
}
