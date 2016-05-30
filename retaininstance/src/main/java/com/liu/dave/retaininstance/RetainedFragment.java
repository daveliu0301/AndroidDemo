package com.liu.dave.retaininstance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

/**
 * Created by LiuDong on 2016/5/30.
 */
public class RetainedFragment extends Fragment {
    private ProgressBar mProgressBar;
    private int mPosition;
    private boolean mReady, mQuiting;
    private final Thread mThread = new Thread() {
        @Override
        public void run() {
            int max = 10000;
            while (true) {
                synchronized (this) {
                    while (!mReady || mPosition >= max) {
                        if (mQuiting)
                            return;
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    mPosition++;
                    max = mProgressBar.getMax();
                    mProgressBar.setProgress(mPosition);
                }
                synchronized (this) {
                    try {
                        wait(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mThread.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getTargetFragment().getView().findViewById(R.id.progress_horizontal);
        synchronized (mThread) {
            mReady = true;
            mThread.notify();
        }
    }

    @Override
    public void onDestroy() {
        synchronized (mThread) {
            mReady = false;
            mQuiting = true;
            mThread.notify();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        synchronized (mThread) {
            mProgressBar = null;
            mReady = false;
            mThread.notify();
        }
        super.onDetach();
    }

    public void restart() {
        synchronized (mThread) {
            mPosition = 0;
            mThread.notify();
        }
    }
}
