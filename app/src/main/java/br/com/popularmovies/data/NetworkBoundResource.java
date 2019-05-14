package br.com.popularmovies.data;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.source.ApiResponse;
import retrofit2.Response;

//TODO Create the NetworkBoundResource
public abstract class NetworkBoundResource<ResultType, RequestType> {

    final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource() {
        result.setValue(Resource.<ResultType>loading());
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                setValue(Resource.<ResultType>loading());
                result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
                    @Override
                    public void onChanged(ApiResponse<RequestType> requestTypeApiResponse) {

                    }
                });
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    @WorkerThread
    protected RequestType processResponse(Response<RequestType> response) {
        return response.body();
    }

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @MainThread
    protected abstract Boolean shouldFetch(ResultType data);

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
