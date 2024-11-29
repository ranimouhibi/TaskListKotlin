import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListApp()
        }
    }
}

@Composable
fun TaskListApp() {
    // Etat de la liste des tâches, initialement avec 100 tâches.
    var tasks by remember { mutableStateOf((0..100).map { "Task # $it" }.toMutableList()) }

    // Etat des cases à cocher pour chaque tâche.
    var checkedStates by remember { mutableStateOf(List(tasks.size) { false }) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                tasks = tasks.plus("Task # ${tasks.size}").toMutableList()
                checkedStates = checkedStates.plus(false)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add one")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Afficher la liste des tâches
        LazyColumn {
            itemsIndexed(tasks) { index, task ->
                TaskRow(
                    task = task,
                    checked = checkedStates[index],
                    onCheckedChange = { isChecked ->
                        checkedStates = checkedStates.toMutableList().also { it[index] = isChecked }
                    },
                    onDelete = {
                        tasks = tasks.toMutableList().also { it.removeAt(index) }
                        checkedStates = checkedStates.toMutableList().also { it.removeAt(index) }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = task, modifier = Modifier.weight(1f))

        Checkbox(checked = checked, onCheckedChange = onCheckedChange)

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete task"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    TaskListApp()
}
