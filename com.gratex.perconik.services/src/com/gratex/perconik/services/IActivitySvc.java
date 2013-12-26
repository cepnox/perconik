
package com.gratex.perconik.services;

import java.util.concurrent.Future;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.Response;
import javax.xml.ws.ResponseWrapper;
import com.gratex.perconik.services.activity.ActivityDto;
import com.gratex.perconik.services.activity.ActivityFilter;
import com.gratex.perconik.services.activity.ArrayOfActivityDto;
import com.gratex.perconik.services.activity.CommitActivityResponse;
import com.gratex.perconik.services.activity.GetActivitiesResponse;
import com.gratex.perconik.services.activity.GetActivityResponse;
import com.gratex.perconik.services.activity.ObjectFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IActivitySvc", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IActivitySvc {


    /**
     * 
     * @param activity
     * @return
     *     returns javax.xml.ws.Response<com.gratex.perconik.services.activity.CommitActivityResponse>
     */
    @WebMethod(operationName = "CommitActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/CommitActivity")
    @RequestWrapper(localName = "CommitActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivity")
    @ResponseWrapper(localName = "CommitActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivityResponse")
    public Response<CommitActivityResponse> commitActivityAsync(
        @WebParam(name = "Activity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityDto activity);

    /**
     * 
     * @param asyncHandler
     * @param activity
     * @return
     *     returns java.util.concurrent.Future<? extends java.lang.Object>
     */
    @WebMethod(operationName = "CommitActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/CommitActivity")
    @RequestWrapper(localName = "CommitActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivity")
    @ResponseWrapper(localName = "CommitActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivityResponse")
    public Future<?> commitActivityAsync(
        @WebParam(name = "Activity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityDto activity,
        @WebParam(name = "asyncHandler", targetNamespace = "")
        AsyncHandler<CommitActivityResponse> asyncHandler);

    /**
     * 
     * @param activity
     */
    @WebMethod(operationName = "CommitActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/CommitActivity")
    @RequestWrapper(localName = "CommitActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivity")
    @ResponseWrapper(localName = "CommitActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.CommitActivityResponse")
    public void commitActivity(
        @WebParam(name = "Activity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityDto activity);

    /**
     * 
     * @param guid
     * @return
     *     returns javax.xml.ws.Response<com.gratex.perconik.services.activity.GetActivityResponse>
     */
    @WebMethod(operationName = "GetActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivity")
    @RequestWrapper(localName = "GetActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivity")
    @ResponseWrapper(localName = "GetActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivityResponse")
    public Response<GetActivityResponse> getActivityAsync(
        @WebParam(name = "guid", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        String guid);

    /**
     * 
     * @param guid
     * @param asyncHandler
     * @return
     *     returns java.util.concurrent.Future<? extends java.lang.Object>
     */
    @WebMethod(operationName = "GetActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivity")
    @RequestWrapper(localName = "GetActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivity")
    @ResponseWrapper(localName = "GetActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivityResponse")
    public Future<?> getActivityAsync(
        @WebParam(name = "guid", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        String guid,
        @WebParam(name = "asyncHandler", targetNamespace = "")
        AsyncHandler<GetActivityResponse> asyncHandler);

    /**
     * 
     * @param guid
     * @return
     *     returns com.gratex.perconik.services.activity.ActivityDto
     */
    @WebMethod(operationName = "GetActivity", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivity")
    @WebResult(name = "GetActivityResult", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
    @RequestWrapper(localName = "GetActivity", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivity")
    @ResponseWrapper(localName = "GetActivityResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivityResponse")
    public ActivityDto getActivity(
        @WebParam(name = "guid", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        String guid);

    /**
     * 
     * @param filter
     * @return
     *     returns javax.xml.ws.Response<com.gratex.perconik.services.activity.GetActivitiesResponse>
     */
    @WebMethod(operationName = "GetActivities", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivities")
    @RequestWrapper(localName = "GetActivities", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivities")
    @ResponseWrapper(localName = "GetActivitiesResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivitiesResponse")
    public Response<GetActivitiesResponse> getActivitiesAsync(
        @WebParam(name = "filter", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityFilter filter);

    /**
     * 
     * @param asyncHandler
     * @param filter
     * @return
     *     returns java.util.concurrent.Future<? extends java.lang.Object>
     */
    @WebMethod(operationName = "GetActivities", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivities")
    @RequestWrapper(localName = "GetActivities", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivities")
    @ResponseWrapper(localName = "GetActivitiesResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivitiesResponse")
    public Future<?> getActivitiesAsync(
        @WebParam(name = "filter", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityFilter filter,
        @WebParam(name = "asyncHandler", targetNamespace = "")
        AsyncHandler<GetActivitiesResponse> asyncHandler);

    /**
     * 
     * @param filter
     * @return
     *     returns com.gratex.perconik.services.activity.ArrayOfActivityDto
     */
    @WebMethod(operationName = "GetActivities", action = "http://www.gratex.com/PerConIk/IActivitySvc/IActivitySvc/GetActivities")
    @WebResult(name = "GetActivitiesResult", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
    @RequestWrapper(localName = "GetActivities", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivities")
    @ResponseWrapper(localName = "GetActivitiesResponse", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc", className = "com.gratex.perconik.services.activity.GetActivitiesResponse")
    public ArrayOfActivityDto getActivities(
        @WebParam(name = "filter", targetNamespace = "http://www.gratex.com/PerConIk/IActivitySvc")
        ActivityFilter filter);

}
