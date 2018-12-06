# Android-Challenge-app
This is a basic bookstore app based on customized API [here](http://tpbookserver.herokuapp.com/books) for an Android Challenge

### Overview
The app uses MVVM and repository pattern to display a list of books. Clicking on the book leads to the book detailed view. The app uses 
master detail flow for tablet mode.

### Libraries used in the Project
- ViewModel
- LiveData
- RecyclerView
- Retrofit for network
- Room for database.

### Screenshots

**Nexus 6**

BookList View | Detail View | Favorite View 
--- | --- | --- | ---
![BookList View](/images/booklist_view.png) | ![Detail](/images/detail_view.png) | ![Favorite](/images/favorite.png)

**Nexus 7(tablet)**

Master Detail Flow| Favorite View 
--- | ---
![BookList View](/images/booklist_tablet.png) | ![Favorite](/images/favorite_tablet.png)


## License

This project submitted by Henna Singh as a part of Android Challenge.

Copyright (c) 2018 Henna Singh

```
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


