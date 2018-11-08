package com.artist.web.bookstore.network;

import com.artist.web.bookstore.model.BookDetail;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;

public class WebRepositoryTest {

    private static final int BOOK_ID =100;

        /*
    Test the API response if it returns LiveData when the network call is made
    Help the SO post: https://stackoverflow.com/a/45166524/8132331
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testApiResponse_AfterMakingNetworkCall_ShouldReturnCallback() {

        BooksApi mockedService = Mockito.mock(BooksApi.class);
        final Call<BookDetail> mockedCall = Mockito.mock(Call.class);

        Mockito.when(mockedService.getSingleBookDetail(BOOK_ID)).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<BookDetail> callback = invocation.getArgument(0);
                callback.onResponse(mockedCall, Response.success(new BookDetail()));

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));
    }

}