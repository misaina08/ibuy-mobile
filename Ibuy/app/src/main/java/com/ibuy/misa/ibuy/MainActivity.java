package com.ibuy.misa.ibuy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import modele.Client;
import services.ObjetsStatiques;
import services.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchView.OnQueryTextListener {
    MaterialSearchView searchView;
    NavigationView navigationView;
    TextView tNomMenu;
    TextView tEmailMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Actualité");

        initObjetstatiques();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tNomMenu = (TextView) findViewById(R.id.tNomMenu);
        tEmailMenu = (TextView) findViewById(R.id.tEmailMenu);
        updateMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Fragment fragment = null;
            if (id == R.id.nav_accueil) {
                fragment = new AccueilFragment();
                getSupportActionBar().setTitle("Actualités");
            } else if (id == R.id.nav_menu_inscrire) {
                fragment = new InscriptionFragment();
                getSupportActionBar().setTitle("Inscription");
            } else if (id == R.id.nav_menu_connect) {
                fragment = new ConnexionFragment();
                getSupportActionBar().setTitle("Connexion");
            } else if (id == R.id.nav_menu_profil) {
                getSupportActionBar().setTitle("Mon profil");
            } else if (id == R.id.nav_menu_magasinsfavoris) {
                fragment = new AbonnementFragment();
                getSupportActionBar().setTitle("Abonnements");
            } else if (id == R.id.nav_menu_mesrecherches) {
                fragment = new MesRecherchesFragment();
                getSupportActionBar().setTitle("Mes recherches");
            } else if (id == R.id.nav_menu_evt) {
                fragment = new EvtClientFragment();
                getSupportActionBar().setTitle("Mes évènements");
            } else if (id == R.id.nav_menu_souhaits) {
                fragment = new ListeSouhaitsFragment();
                getSupportActionBar().setTitle("Liste de souhaits");
            } else if (id == R.id.nav_menu_notif) {
                fragment = new NotificationFragment();
                getSupportActionBar().setTitle("Notification");
            } else if (id == R.id.nav_menu_deconnect) {
                SessionManager sessionManager = new SessionManager(getBaseContext());
                sessionManager.destroyUserSession();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_menu_panier) {
                fragment = new PanierFragment();
                getSupportActionBar().setTitle("Mon panier");
            }else if (id == R.id.nav_menu_commande) {
                fragment = new CommandesFragment();
                getSupportActionBar().setTitle("Mes commandes");
            }
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Modifie la visibilité des menu selon la session
     */
    public void updateMenu() {
        try {
            SessionManager sessionManager = new SessionManager(this);
            if (SessionManager.estConnecte()) {
                navigationView.getMenu().findItem(R.id.nav_menu_inscrire).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_connect).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_profil).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_magasinsfavoris).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_mesrecherches).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_evt).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_notif).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_deconnect).setVisible(true);
                Client client = SessionManager.getClientConnected();
                tEmailMenu.setText(client.getEmail());
                tNomMenu.setText(client.getNom() + " " + client.getPrenom());
            } else {
                navigationView.getMenu().findItem(R.id.nav_menu_inscrire).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_connect).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_menu_profil).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_magasinsfavoris).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_mesrecherches).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_evt).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_notif).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_menu_deconnect).setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, ResultSearchActivity.class);
        intent.putExtra("query", query);
        this.startActivity(intent);
        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void initObjetstatiques() {
        ObjetsStatiques objetsStatiques = new ObjetsStatiques();
        objetsStatiques.init();
    }

}
