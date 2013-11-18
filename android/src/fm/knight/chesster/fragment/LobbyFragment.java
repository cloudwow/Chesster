package fm.knight.chesster.fragment;


import fm.knight.chesster.R;
import fm.knight.chesster.activity.MainActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class LobbyFragment extends Fragment {

  private ImageView startView;

  public LobbyFragment() {
  }

  @Override
  public View onCreateView(
                           LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View myView = inflater.inflate(R.layout.fragment_lobby, container, false);

    startView = (ImageView) myView.findViewById(R.id.lobby_button_start);
    if (startView==null) {
      throw new RuntimeException("start button not found in layout");
    }
    startView.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          Intent game = new Intent(getActivity().getApplicationContext(),MainActivity.class);
          startActivity(game);
        }
      });

    return myView;
  }
}
