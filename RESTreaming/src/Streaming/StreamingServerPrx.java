//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.6
//
// <auto-generated>
//
// Generated from file `Streaming.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Streaming;

public interface StreamingServerPrx extends com.zeroc.Ice.ObjectPrx
{
    default music[] shareTracklist()
    {
        return shareTracklist(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default music[] shareTracklist(java.util.Map<String, String> context)
    {
        return _iceI_shareTracklistAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<music[]> shareTracklistAsync()
    {
        return _iceI_shareTracklistAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<music[]> shareTracklistAsync(java.util.Map<String, String> context)
    {
        return _iceI_shareTracklistAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<music[]> _iceI_shareTracklistAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<music[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "shareTracklist", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     music[] ret;
                     ret = MusicListHelper.read(istr);
                     return ret;
                 });
        return f;
    }

    default int getStreamNumber()
    {
        return getStreamNumber(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default int getStreamNumber(java.util.Map<String, String> context)
    {
        return _iceI_getStreamNumberAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getStreamNumberAsync()
    {
        return _iceI_getStreamNumberAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getStreamNumberAsync(java.util.Map<String, String> context)
    {
        return _iceI_getStreamNumberAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> _iceI_getStreamNumberAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getStreamNumber", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     int ret;
                     ret = istr.readInt();
                     return ret;
                 });
        return f;
    }

    default void transferFragment(String ref, byte[] f)
    {
        transferFragment(ref, f, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void transferFragment(String ref, byte[] f, java.util.Map<String, String> context)
    {
        _iceI_transferFragmentAsync(ref, f, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> transferFragmentAsync(String ref, byte[] f)
    {
        return _iceI_transferFragmentAsync(ref, f, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> transferFragmentAsync(String ref, byte[] f, java.util.Map<String, String> context)
    {
        return _iceI_transferFragmentAsync(ref, f, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param iceP_f -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_transferFragmentAsync(String iceP_ref, byte[] iceP_f, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "transferFragment", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                     ostr.writeByteSeq(iceP_f);
                 }, null);
        return f;
    }

    default String saveTrack(String ref, music m)
    {
        return saveTrack(ref, m, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String saveTrack(String ref, music m, java.util.Map<String, String> context)
    {
        return _iceI_saveTrackAsync(ref, m, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> saveTrackAsync(String ref, music m)
    {
        return _iceI_saveTrackAsync(ref, m, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> saveTrackAsync(String ref, music m, java.util.Map<String, String> context)
    {
        return _iceI_saveTrackAsync(ref, m, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param iceP_m -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_saveTrackAsync(String iceP_ref, music iceP_m, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "saveTrack", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                     music.ice_write(ostr, iceP_m);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default String modifyTrack(String ref, music m, music n)
    {
        return modifyTrack(ref, m, n, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String modifyTrack(String ref, music m, music n, java.util.Map<String, String> context)
    {
        return _iceI_modifyTrackAsync(ref, m, n, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> modifyTrackAsync(String ref, music m, music n)
    {
        return _iceI_modifyTrackAsync(ref, m, n, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> modifyTrackAsync(String ref, music m, music n, java.util.Map<String, String> context)
    {
        return _iceI_modifyTrackAsync(ref, m, n, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param iceP_m -
     * @param iceP_n -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_modifyTrackAsync(String iceP_ref, music iceP_m, music iceP_n, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "modifyTrack", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                     music.ice_write(ostr, iceP_m);
                     music.ice_write(ostr, iceP_n);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default String deleteTrack(String ref, music m)
    {
        return deleteTrack(ref, m, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String deleteTrack(String ref, music m, java.util.Map<String, String> context)
    {
        return _iceI_deleteTrackAsync(ref, m, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> deleteTrackAsync(String ref, music m)
    {
        return _iceI_deleteTrackAsync(ref, m, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> deleteTrackAsync(String ref, music m, java.util.Map<String, String> context)
    {
        return _iceI_deleteTrackAsync(ref, m, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param iceP_m -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_deleteTrackAsync(String iceP_ref, music iceP_m, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "deleteTrack", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                     music.ice_write(ostr, iceP_m);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default String streamTrack(music m)
    {
        return streamTrack(m, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String streamTrack(music m, java.util.Map<String, String> context)
    {
        return _iceI_streamTrackAsync(m, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> streamTrackAsync(music m)
    {
        return _iceI_streamTrackAsync(m, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> streamTrackAsync(music m, java.util.Map<String, String> context)
    {
        return _iceI_streamTrackAsync(m, context, false);
    }

    /**
     * @hidden
     * @param iceP_m -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_streamTrackAsync(music iceP_m, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "streamTrack", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     music.ice_write(ostr, iceP_m);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default void pauseTrack(String ref)
    {
        pauseTrack(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void pauseTrack(String ref, java.util.Map<String, String> context)
    {
        _iceI_pauseTrackAsync(ref, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> pauseTrackAsync(String ref)
    {
        return _iceI_pauseTrackAsync(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> pauseTrackAsync(String ref, java.util.Map<String, String> context)
    {
        return _iceI_pauseTrackAsync(ref, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_pauseTrackAsync(String iceP_ref, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "pauseTrack", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                 }, null);
        return f;
    }

    default void resumeTrack(String ref)
    {
        resumeTrack(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void resumeTrack(String ref, java.util.Map<String, String> context)
    {
        _iceI_resumeTrackAsync(ref, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> resumeTrackAsync(String ref)
    {
        return _iceI_resumeTrackAsync(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> resumeTrackAsync(String ref, java.util.Map<String, String> context)
    {
        return _iceI_resumeTrackAsync(ref, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_resumeTrackAsync(String iceP_ref, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "resumeTrack", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                 }, null);
        return f;
    }

    default void stopTrack(String ref)
    {
        stopTrack(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void stopTrack(String ref, java.util.Map<String, String> context)
    {
        _iceI_stopTrackAsync(ref, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> stopTrackAsync(String ref)
    {
        return _iceI_stopTrackAsync(ref, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> stopTrackAsync(String ref, java.util.Map<String, String> context)
    {
        return _iceI_stopTrackAsync(ref, context, false);
    }

    /**
     * @hidden
     * @param iceP_ref -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_stopTrackAsync(String iceP_ref, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "stopTrack", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_ref);
                 }, null);
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static StreamingServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static StreamingServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static StreamingServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static StreamingServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static StreamingServerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static StreamingServerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, StreamingServerPrx.class, _StreamingServerPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default StreamingServerPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (StreamingServerPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default StreamingServerPrx ice_adapterId(String newAdapterId)
    {
        return (StreamingServerPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default StreamingServerPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (StreamingServerPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default StreamingServerPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (StreamingServerPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default StreamingServerPrx ice_invocationTimeout(int newTimeout)
    {
        return (StreamingServerPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default StreamingServerPrx ice_connectionCached(boolean newCache)
    {
        return (StreamingServerPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default StreamingServerPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (StreamingServerPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default StreamingServerPrx ice_secure(boolean b)
    {
        return (StreamingServerPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default StreamingServerPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (StreamingServerPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default StreamingServerPrx ice_preferSecure(boolean b)
    {
        return (StreamingServerPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default StreamingServerPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (StreamingServerPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default StreamingServerPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (StreamingServerPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default StreamingServerPrx ice_collocationOptimized(boolean b)
    {
        return (StreamingServerPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default StreamingServerPrx ice_twoway()
    {
        return (StreamingServerPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default StreamingServerPrx ice_oneway()
    {
        return (StreamingServerPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default StreamingServerPrx ice_batchOneway()
    {
        return (StreamingServerPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default StreamingServerPrx ice_datagram()
    {
        return (StreamingServerPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default StreamingServerPrx ice_batchDatagram()
    {
        return (StreamingServerPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default StreamingServerPrx ice_compress(boolean co)
    {
        return (StreamingServerPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default StreamingServerPrx ice_timeout(int t)
    {
        return (StreamingServerPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default StreamingServerPrx ice_connectionId(String connectionId)
    {
        return (StreamingServerPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default StreamingServerPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (StreamingServerPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Streaming::StreamingServer";
    }
}
