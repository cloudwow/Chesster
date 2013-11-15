package fm.knight.chesster.activity;


import fm.knight.chesster.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LobbyFragment extends Fragment {

  private Button startButton;
  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View myView = inflater.inflate(R.layout.fragment_lobby, container, false);

    startButton = (Button) myView.findViewById(R.id.lobby_start_button);
    startButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(
          View v) {}
    });
      
    return myView;
  }
}
