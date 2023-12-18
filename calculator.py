import tkinter as tk

def on_button_click(symbol):
    current_text = entry_var.get()
    entry_var.set(current_text + str(symbol))

def on_clear_click():
    entry_var.set("")

def on_equals_click():
    try:
        result = eval(entry_var.get())
        entry_var.set(result)
    except Exception as e:
        entry_var.set("Error")

root = tk.Tk()
root.title("Simple Calculator")

entry_var = tk.StringVar()

entry = tk.Entry(root, textvariable=entry_var, font=("Arial", 20), justify="right")
entry.grid(row=0, column=0, columnspan=4)

buttons = [
    ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
    ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
    ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
    ('0', 4, 0), ('C', 4, 1), ('=', 4, 2), ('+', 4, 3),
]

for (text, row, column) in buttons:
    if text == '=':
        button = tk.Button(root, text=text, command=on_equals_click, height=2, width=5, font=("Arial", 14))
    else:
        button = tk.Button(root, text=text, command=lambda t=text: on_button_click(t), height=2, width=5, font=("Arial", 14))
    button.grid(row=row, column=column, sticky="nsew", padx=5, pady=5)

# Membuat tombol "=" lebih lebar
root.grid_columnconfigure(2, weight=2)

# Membuat semua baris dan kolom grid dapat menyesuaikan ukuran window
for i in range(5):
    root.grid_rowconfigure(i, weight=1)
    root.grid_columnconfigure(i, weight=1)

root.mainloop()
