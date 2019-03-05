package com.qiuchenly.comicparse.Http.BikaApi;

import com.qiuchenly.comicparse.Http.BikaApi.requests.AdjustExpBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.AvatarBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.ChangePasswordBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.CommentBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.ForgotPasswordBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.RegisterBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.SignInBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.UpdateProfileBody;
import com.qiuchenly.comicparse.Http.BikaApi.requests.UpdateUserTitleBody;
import com.qiuchenly.comicparse.Http.BikaApi.responses.ActionResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.BannersResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.CategoryResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.ChatroomListResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.ComicDetailResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.ComicRandomListResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.CommentPostToTopResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.AnnouncementsResponse.AnnouncementsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ApplicationsResponse.ApplicationsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicListResponse.ComicListResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicPageResponse.ComicPagesResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.CommentsResponse.CommentsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.GameDetailResponse.GameDetailResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.GameListResponse.GameListResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.PicaAppsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.PostCommentResponse.PostCommentResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ProfileCommentsResponse.ProfileCommentsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.InitialResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.KeywordsResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.LeaderboardKnightResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.LeaderboardResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.MessageResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.PunchInResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.PutAvatarResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.RegisterResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.SignInResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.TagListResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.UserProfileDirtyResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.UserProfileResponse;
import com.qiuchenly.comicparse.Http.BikaApi.responses.WakaInitResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("utils/adjust-exp")
    Call<RegisterResponse> adjustExp(@Header("authorization") String str, @Body AdjustExpBody adjustExpBody);

    @POST("comics/{comicId}/favourite")
    Call<GeneralResponse<ActionResponse>> bookmarkComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @POST("auth/forgot-password")
    Call<RegisterResponse> forgotPassword(@Body ForgotPasswordBody forgotPasswordBody);

    @GET("announcements")
    Call<GeneralResponse<AnnouncementsResponse>> getAnnouncements(@Header("authorization") String str, @Query("page") int i);

    @GET("applications?platform=android")
    Call<GeneralResponse<ApplicationsResponse>> getApplications(@Header("authorization") String str, @Query("page") int i);

    @GET("banners")
    Call<GeneralResponse<BannersResponse>> getBanners(@Header("authorization") String str);

    @GET("categories")
    Call<GeneralResponse<CategoryResponse>> getCategories(@Header("authorization") String str);

    @GET("chat")
    Call<GeneralResponse<ChatroomListResponse>> getChatroomList(@Header("authorization") String str);

    @GET("comics/{comicId}/eps")
    Call<GeneralResponse<ComicEpisodeResponse>> getComicEpisode(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);

    @GET("comics")
    Call<GeneralResponse<ComicListResponse>> getComicList(@Header("authorization") String authorization, @Query("page") int page, @Query("c") String c, @Query("t") String t, @Query("a") String a, @Query("f") String f, @Query("s") String s, @Query("ct") String ct, @Query("ca") String ca);

    @GET("comics/search")
    Call<GeneralResponse<ComicListResponse>> getComicListWithSearchKey(@Header("authorization") String str, @Query("page") int i, @Query("q") String str2);

    @GET("comics/{comicId}")
    Call<GeneralResponse<ComicDetailResponse>> getComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @GET("comics/{comicId}/comments")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithComicId(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);

    @GET("comments/{commentId}/childrens")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithCommentId(@Header("authorization") String str, @Path("commentId") String str2, @Query("page") int i);

    @GET("games/{gameId}/comments")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithGameId(@Header("authorization") String str, @Path("gameId") String str2, @Query("page") int i);

    @GET("users/favourite")
    Call<GeneralResponse<ComicListResponse>> getFavourite(@Header("authorization") String str, @Query("page") int i);

    @GET("games/{gameId}")
    Call<GeneralResponse<GameDetailResponse>> getGameDetail(@Header("authorization") String str, @Path("gameId") String str2);

    @GET("games")
    Call<GeneralResponse<GameListResponse>> getGameList(@Header("authorization") String str, @Query("page") int i);

    @GET("init?platform=android")
    Call<GeneralResponse<InitialResponse>> getInit(@Header("authorization") String str);

    @GET("keywords")
    Call<GeneralResponse<KeywordsResponse>> getKeywords(@Header("authorization") String str);

    @GET("comics/knight-leaderboard")
    Call<GeneralResponse<LeaderboardKnightResponse>> getKnightLeaderboard(@Header("authorization") String str);

    @GET("comics/leaderboard")
    Call<GeneralResponse<LeaderboardResponse>> getLeaderboard(@Header("authorization") String str, @Query("tt") String str2, @Query("ct") String str3);

    @GET("eps/{epsId}/download")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithEpisodeId(@Header("authorization") String str, @Path("epsId") String str2);

    @GET("eps/{epsId}/pages")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithEpisodeId(@Header("authorization") String str, @Path("epsId") String str2, @Query("page") int i);

    @GET("comics/{comicId}/order/{order}/pages")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithOrder(@Header("authorization") String str, @Path("comicId") String str2, @Path("order") int i, @Query("page") int i2);

    @GET("pica-apps")
    Call<GeneralResponse<PicaAppsResponse>> getPicaApps(@Header("authorization") String str);

    @GET("users/my-comments")
    Call<GeneralResponse<ProfileCommentsResponse>> getProfileComments(@Header("authorization") String str, @Query("page") int i);

    @GET("comics/random")
    Call<GeneralResponse<ComicRandomListResponse>> getRandomComicList(@Header("authorization") String str);

    @GET("tags")
    Call<GeneralResponse<TagListResponse>> getTags(@Header("authorization") String str);

    @GET("users/profile")
    Call<GeneralResponse<UserProfileResponse>> getUserProfile(@Header("authorization") String str);

    @GET("users/{userId}/profile")
    Call<GeneralResponse<UserProfileResponse>> getUserProfileWithUserId(@Header("authorization") String str, @Path("userId") String str2);

    @GET("init")
    Call<WakaInitResponse> getWakaInit();

    @POST("comments/{commentId}/hide")
    Call<GeneralResponse<MessageResponse>> hideCommentWithCommentId(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("comics/{comicId}/like")
    Call<GeneralResponse<ActionResponse>> likeComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @POST("comments/{commentId}/like")
    Call<GeneralResponse<ActionResponse>> likeCommentWithId(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("games/{gameId}/like")
    Call<GeneralResponse<ActionResponse>> likeGameWithId(@Header("authorization") String str, @Path("gameId") String str2);

    @POST("comments/{commentId}/top")
    Call<GeneralResponse<CommentPostToTopResponse>> postCommentToTheTop(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("comics/{comicId}/comments")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithComicId(@Header("authorization") String str, @Path("comicId") String str2, @Body CommentBody commentBody);

    @POST("comments/{commentId}")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithCommentId(@Header("authorization") String str, @Path("commentId") String str2, @Body CommentBody commentBody);

    @POST("games/{gameId}/comments")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithGameId(@Header("authorization") String str, @Path("gameId") String str2, @Body CommentBody commentBody);

    @POST("users/{userId}/dirty")
    Call<GeneralResponse<UserProfileDirtyResponse>> postDirty(@Header("authorization") String str, @Path("userId") String str2);

    @POST("users/punch-in")
    Call<GeneralResponse<PunchInResponse>> punchIn(@Header("authorization") String str);

    @PUT("users/avatar")
    Call<GeneralResponse<PutAvatarResponse>> putUserAvatar(@Header("authorization") String str, @Body AvatarBody avatarBody);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterBody registerBody);

    @POST("auth/resend-activation")
    Call<RegisterResponse> resendActivation(@Body ForgotPasswordBody forgotPasswordBody);

    @POST("auth/sign-in")
    Call<GeneralResponse<SignInResponse>> signIn(@Body SignInBody signInBody);

    @PUT("users/profile")
    Call<RegisterResponse> updateProfile(@Header("authorization") String str, @Body UpdateProfileBody updateProfileBody);

    @PUT("users/password")
    Call<RegisterResponse> updateUserPassword(@Header("authorization") String str, @Body ChangePasswordBody changePasswordBody);

    @PUT("users/{userId}/title")
    Call<RegisterResponse> updateUserTitle(@Header("authorization") String str, @Path("userId") String str2, @Body UpdateUserTitleBody updateUserTitleBody);
}