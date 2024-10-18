package ru.kkuzmichev.simpleappforespresso.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.kkuzmichev.simpleappforespresso.EspressoIdlingResources;
import ru.kkuzmichev.simpleappforespresso.R;
import ru.kkuzmichev.simpleappforespresso.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<GalleryItem> itemList = new ArrayList<>();
    private FloatingActionButton fab;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setClickable(false);

        progressBar = root.findViewById(R.id.progress_bar);
        recyclerView = root.findViewById(R.id.recycle_view);
        fakeLoadData();

        setLists();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new GalleryAdapter(itemList));
        return root;
    }

    private void setLists() {
        for (int i = 0; i < 10; i++) {
            itemList.add(new GalleryItem("My title", "My description", (i+1)));
        }
    }


    private void fakeLoadData() {
        EspressoIdlingResources.increment(); // * Инкрементируется, увеличивается счетчик на 1
        progressBar.setVisibility(View.VISIBLE); // Показываем прогресс-бар, указывая пользователю, что загрузка идет
        recyclerView.setVisibility(View.INVISIBLE); // Скрываем recyclerView, пока данные загружаются
        Handler handler = new Handler(); // Создаем новый обработчик для выполнения кода с задержкой
        handler.postDelayed(new Runnable() { // Запускаем новый Runnable с задержкой
            @Override
            public void run() {
                {
                    recyclerView.setAdapter(new GalleryAdapter(itemList)); // * Устанавливаем адаптер для recyclerView с загруженным списком элементов
                    progressBar.setVisibility(View.INVISIBLE); // Скрываем прогресс-бар, так как загрузка завершена
                    recyclerView.setVisibility(View.VISIBLE); // Показываем recyclerView, чтобы отобразить загруженные данные
                    EspressoIdlingResources.decrement(); // * Уменьшаем счетчик IdlingResource, указывая на завершение загрузки
                }
            }
        }, 1500); // Устанавливаем задержку выполнения в 1.5 секунды
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}