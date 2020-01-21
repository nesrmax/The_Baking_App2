package com.example.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapters.StepAdapter;
import com.example.myapplication.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static com.example.myapplication.Adapters.StepAdapter.position;

public class FregmShowDetail extends android.app.Fragment {
    private SimpleExoPlayer video_player;
    private PlayerView playerView;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    TextView Description;
    BottomNavigationView bottomNavigationView;
    Uri uri;
    String VideoUrl;
    String thunmbail;
    Uri url;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();
    private ComponentListener componentListener;

    public FregmShowDetail() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disc_screen, container, false);
        componentListener = new ComponentListener();
        playerView = view.findViewById(R.id.videoview);
        Description = view.findViewById(R.id.Description);
        Description.setText(StepAdapter.arrayList.get(StepAdapter.position).getDescription());
        bottomNavigationView = view.findViewById(R.id.bottom_nav);
        imageView = view.findViewById(R.id.thun);
        VideoUrl = StepAdapter.arrayList.get(StepAdapter.position).getVideo_URL();
        thunmbail = StepAdapter.arrayList.get(StepAdapter.position).getThumb_URL();
        sharedPreferences = getActivity().getSharedPreferences("myfile", MODE_PRIVATE);

        if (!VideoUrl.isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            uri = Uri.parse(VideoUrl);


        } else if (!thunmbail.isEmpty()) {
            playerView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(thunmbail).into(imageView);
            hideSystemUi();
        } else {
            playerView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            uri = Uri.parse("");
        }


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.Next:
                        if (position < TheFragment.stepAdapter.getItemCount() - 1) {
                            VideoUrl = StepAdapter.arrayList.get(position + 1).getVideo_URL();
                            thunmbail = StepAdapter.arrayList.get(position + 1).getThumb_URL();
                            Description.setText(StepAdapter.arrayList.get(position + 1).getDescription());
                            if (!VideoUrl.isEmpty()) {
                                playerView.setVisibility(View.VISIBLE);
                                url = Uri.parse(VideoUrl);
                                //hideSystemUi();
                                releasePlayer();
                                initializePlayer(url, true);


                            } else if (!thunmbail.isEmpty()) {
                                releasePlayer();
                                //hideSystemUi();
                                playerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                Picasso.with(getActivity()).load(thunmbail).into(imageView);

                            } else {
                                releasePlayer();
                                //hideSystemUi();
                                playerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.GONE);
                                uri = Uri.parse("");
                            }
                            position = position + 1;
                        }
                        break;
                    ///////////////////////////////////////////////////////////////////////

                    ////////////////For ActionBack/////////////////////////////

                    case R.id.Previous:

                        if (position > 0) {

                            VideoUrl = StepAdapter.arrayList.get(position - 1).getVideo_URL();
                            thunmbail = StepAdapter.arrayList.get(position - 1).getThumb_URL();
                            Description.setText(StepAdapter.arrayList.get(position - 1).getDescription());

                            if (!VideoUrl.isEmpty()) {
                                playerView.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);
                                url = Uri.parse(VideoUrl);
                                releasePlayer();
                                initializePlayer(url, true);
                            } else if (!thunmbail.isEmpty()) {
                                releasePlayer();
                                playerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                Picasso.with(getActivity()).load(thunmbail).into(imageView);
                            } else {
                                releasePlayer();
                                playerView.setVisibility(View.GONE);
                                imageView.setVisibility(View.GONE);
                                uri = Uri.parse("");
                            }
                            position = position - 1;
                        }
                        break;
                }
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("uri", String.valueOf(uri));
        outState.putString("url", String.valueOf(url));
        outState.putLong("pos", sharedPreferences.getLong("pos", 0));
        outState.putInt("cur", sharedPreferences.getInt("cur", 0));

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("pos");
            currentWindow = savedInstanceState.getInt("cur");
            uri = Uri.parse(savedInstanceState.getString("uri"));
            url = Uri.parse(savedInstanceState.getString("url"));
            if (url != null) {
                initializePlayer(url, false);

            }

            initializePlayer(uri, false);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (video_player == null) {
            hideSystemUi();
            initializePlayer(uri, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if (video_player == null) {
            initializePlayer(uri, true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
        SharedPreferences.Editor myedit = sharedPreferences.edit();
        myedit.putLong("pos", playbackPosition);
        myedit.putInt("cur", currentWindow);
        myedit.commit();

    }


    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
        SharedPreferences.Editor myedit = sharedPreferences.edit();
        myedit.putLong("pos", playbackPosition);
        myedit.putInt("cur", currentWindow);
        myedit.commit();

    }

    @Override
    public void onDetach() {
        releasePlayer();
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        releasePlayer();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void initializePlayer(Uri uri, boolean check) {
        if (check) {
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            // using a DefaultTrackSelector with an adaptive video selection factory
            video_player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
            video_player.addListener(componentListener);
            video_player.addVideoDebugListener(componentListener);
            video_player.addAudioDebugListener(componentListener);
            playerView.setPlayer(video_player);
            video_player.setPlayWhenReady(playWhenReady);
            video_player.seekTo(currentWindow, playbackPosition);


            MediaSource mediaSource = buildMediaSource(uri);
            video_player.prepare(mediaSource, true, false);

        } else {

            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            video_player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
            video_player.addListener(componentListener);
            video_player.addVideoDebugListener(componentListener);
            video_player.addAudioDebugListener(componentListener);
            playerView.setPlayer(video_player);
            video_player.setPlayWhenReady(true);
            video_player.seekTo(currentWindow, playbackPosition);
            MediaSource mediaSource = buildMediaSource(uri);
            video_player.prepare(mediaSource, false, false);

        }

    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Baking")).
                createMediaSource(uri);
    }


    private void releasePlayer() {
        if (video_player != null) {
            playbackPosition = video_player.getCurrentPosition();
            currentWindow = video_player.getCurrentWindowIndex();
            playWhenReady = video_player.getPlayWhenReady();
            video_player.removeListener(componentListener);
            video_player.removeVideoDebugListener(componentListener);
            video_player.removeAudioDebugListener(componentListener);
            video_player.release();
            video_player = null;


        }
    }


    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE
                | SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private class ComponentListener extends Player.DefaultEventListener implements
            VideoRendererEventListener, AudioRendererEventListener {


        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case Player.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
        }


        @Override
        public void onVideoEnabled(DecoderCounters counters) {
        }

        @Override
        public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        }

        @Override
        public void onVideoInputFormatChanged(Format format) {
        }

        @Override
        public void onDroppedFrames(int count, long elapsedMs) {
        }

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        }

        @Override
        public void onRenderedFirstFrame(Surface surface) {
        }

        @Override
        public void onVideoDisabled(DecoderCounters counters) {
        }

        @Override
        public void onAudioEnabled(DecoderCounters counters) {
        }

        @Override
        public void onAudioSessionId(int audioSessionId) {
        }

        @Override
        public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        }

        @Override
        public void onAudioInputFormatChanged(Format format) {
        }

        @Override
        public void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        }

        @Override
        public void onAudioDisabled(DecoderCounters counters) {
        }

    }
}
