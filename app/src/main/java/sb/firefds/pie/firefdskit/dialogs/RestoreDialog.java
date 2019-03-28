package sb.firefds.pie.firefdskit.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.File;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import sb.firefds.pie.firefdskit.R;
import sb.firefds.pie.firefdskit.adapters.BackupAdapter;
import sb.firefds.pie.firefdskit.utils.Constants;

public class RestoreDialog {

    private AlertDialog dialog;
    private File dir;
    private File[] backups;
    private ListView listView;
    private RestoreDialogListener listener;

    public RestoreDialog(AppCompatActivity activity) {
        this.listener = (RestoreDialogListener) activity;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + Constants.BACKUP_DIR;
        LinearLayout linearLayout = new LinearLayout(context);
        listView = new ListView(context);
        linearLayout.addView(listView);
        TextView emptyView = new TextView(context, null, android.R.layout.simple_list_item_1);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (Resources.getSystem().getDisplayMetrics().density * 48)));
        emptyView.setGravity(Gravity.CENTER);
        linearLayout.addView(emptyView);
        emptyView.setText(R.string.no_backups);
        listView.setEmptyView(emptyView);
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        setupData(context);

        listView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            listener.onRestoreBackup(backups[arg2]);
            dialog.dismiss();
        });
        listView.setOnItemLongClickListener((arg0, arg1, arg2, arg3) -> {
            PopupMenu menu = new PopupMenu(context, arg1);
            menu.inflate(R.menu.backup_item);
            menu.setOnMenuItemClickListener(item1 -> {
                if (item1.getItemId() == R.id.action_delete) {
                    backups[arg2].delete();
                    setupData(context);
                }
                return true;
            });
            menu.show();
            return true;
        });
        dialog = builder.setCancelable(true).setTitle(R.string.restore).setView(linearLayout)
                .setPositiveButton(R.string.defaults, (dialog, which) -> listener.onRestoreDefaults())
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        dialog.show();
    }

    private void setupData(Context context) {
        backups = dir.listFiles();
        if (backups == null || backups.length == 0) {
            return;
        }
        BackupAdapter adapter = new BackupAdapter(context, backups);
        listView.setAdapter(adapter);
    }

    public interface RestoreDialogListener {
        void onRestoreDefaults();

        void onRestoreBackup(File file);
    }
}