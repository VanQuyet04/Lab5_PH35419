package com.example.lab5_ph35419

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoryApp()
        }
    }

    @Composable
    fun CategoryApp() {

        val categories = listOf("Fiction", "Mystery", "Science Fiction", "Fantasy", "Adventure", "Historical", "Horror", "Romance")
        val suggestions = listOf("Biography", "Cookbook", "Poetry", "Self-help", "Thriller")

        var selectedCategories by remember { mutableStateOf(setOf<String>()) }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Choose a category:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
 
            AssistChip(onClick = {},
                label = { Text(text = "Need help") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CateGoryChips(
                categories = categories,
                selectedCategories = selectedCategories,
                onCategoriesSelected = { category ->

                    selectedCategories = if (selectedCategories.contains(category))
                        selectedCategories - category
                    else
                        selectedCategories + category

                })

            Spacer(modifier = Modifier.height(16.dp))

            SuggestionChips(

                suggestions = suggestions,
                selectedCategories = selectedCategories,
                onSuggestionSelected = { suggestion ->
                    selectedCategories = selectedCategories + suggestion

                })

            if (selectedCategories.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                SelectedCategoriesChips(selectedCategories = selectedCategories,
                    onCategoriesRemoved = { category ->

                        selectedCategories = selectedCategories - category

                    })

                Spacer(modifier = Modifier.height(4.dp))

                AssistChip(
                    onClick = { selectedCategories = setOf() },
                    label = {
                        Text(
                            text = "Clear selections",
                            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color.DarkGray),
                    border = AssistChipDefaults.assistChipBorder(borderColor = Color.Black)

                )


            }


        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CateGoryChips(
        categories: List<String>,
        selectedCategories: Set<String>,
        onCategoriesSelected: (String) -> Unit

    ) {

        Text("Choose categories: ", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            categories.forEach { categories ->
                FilterChip(
                    selected = selectedCategories.contains(categories),
                    onClick = { onCategoriesSelected(categories) },
                    label = { Text(text = categories) },
                    modifier = Modifier.padding(end = 8.dp)

                )

            }
        }

    }

    @Composable
    fun SuggestionChips(
        suggestions: List<String>,
        selectedCategories: Set<String>,
        onSuggestionSelected: (String) -> Unit
    ) {

        Text(text = "Suggestions:", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            suggestions.forEach { suggestion ->

                val isSelected = selectedCategories.contains(suggestion)
                val chipColors = if (isSelected) {
                    SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color.LightGray
                    )
                } else {
                    SuggestionChipDefaults.suggestionChipColors()
                }

                SuggestionChip(
                    onClick = { onSuggestionSelected(suggestion) },
                    label = { Text(text = suggestion) },
                    colors = chipColors,
                    modifier = Modifier.padding(end = 8.dp)
                )


            }


        }
    }

    @Composable
    fun test(){
        Text(text = "Nhìn con cặc gì, bố mày đang test thôi")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SelectedCategoriesChips(
        selectedCategories: Set<String>,
        onCategoriesRemoved: (String) -> Unit
    ) {
        Text(text = "Selected categories:", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            selectedCategories.forEach { selectedCategory ->
                InputChip(
                    selected = true,
                    onClick = { },
                    label = { Text(text = selectedCategory) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close, contentDescription = "Deselect",
                            modifier = Modifier
                                .clickable {
                                    onCategoriesRemoved(selectedCategory)
                                }
                                .size(18.dp)
                        )
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )

            }
        }

    }

}