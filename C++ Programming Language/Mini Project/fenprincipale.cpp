#include "fenprincipale.h"

FenPrincipale::FenPrincipale()
{
    //Widget Creation
    createActions();
    createMenus();
    createToolsBar();
    createProgressBar();

    //Tabs Generation
    tabs = new QTabWidget;
    tabs->addTab(createTabWebPage("https://www.google.com"), "New Page");
    connect(tabs, SIGNAL(currentChanged(int)), this, SLOT(tabChanged(int)));
    setCentralWidget(tabs);

    //Other Parameters
    setMinimumSize(500,350);
    setWindowTitle("AzizNavigo");
    setWindowIcon(QIcon("C:/Users/Lenovo/Desktop/untitled/img/web.png"));
}

/***********Widget Creation***********/

void FenPrincipale::createActions()
{
    //Open New Tab
    newTabAction = new QAction("&New tab", this);
    newTabAction->setShortcut(QKeySequence("Ctrl+T"));
    connect(newTabAction, SIGNAL(triggered()), this, SLOT(newTab()));
    //Close Tab
    closeTabAction = new QAction("&Close tab", this);
    closeTabAction->setShortcut(QKeySequence("Ctrl+W"));
    connect(closeTabAction, SIGNAL(triggered()), this, SLOT(closeTab()));
    //Close Program
    closeProgramAction = new QAction("&Close program", this);
    closeProgramAction->setShortcut(QKeySequence("Ctrl+Q"));
    connect(closeProgramAction, SIGNAL(triggered()), qApp, SLOT(quit()));

    //Previous Page
    previousPageAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/prev.png"), "&Previous page", this);
    previousPageAction->setShortcut(QKeySequence::Back);
    connect(previousPageAction, SIGNAL(triggered()), this, SLOT(previousPage()));
    //Next Page
    nextPageAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/next.png"), "&Next page", this);
    nextPageAction->setShortcut(QKeySequence::Forward);
    connect(nextPageAction, SIGNAL(triggered()), this, SLOT(nextPage()));
    //Stop Refreshing
    stopRefreshAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/stop.png"), "&Stop refreshing", this);
    connect(stopRefreshAction, SIGNAL(triggered()), this, SLOT(stopRefresh()));
    //Refresh Page
    refreshPageAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/refresh.png"), "&Refresh page", this);
    refreshPageAction->setShortcut(QKeySequence::Refresh);
    connect(refreshPageAction, SIGNAL(triggered()), this, SLOT(refreshPage()));
    //Home Page
    homePageAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/home.png"), "&Home page", this);
    connect(homePageAction, SIGNAL(triggered()), this, SLOT(homePage()));
    //Go to a website
    goAction = new QAction(QIcon("C:/Users/Lenovo/Desktop/untitled/img/go.png"), "&Go to a website", this);
    connect(goAction, SIGNAL(triggered()), this, SLOT(loadPage()));

    //About Aziz Navigo
    aboutNavigoAction = new QAction("&About Aziz Navigo", this);
    aboutNavigoAction->setShortcut(QKeySequence::HelpContents);
    connect(aboutNavigoAction, SIGNAL(triggered()), this, SLOT(aboutAzizNavigo()));
    //About Qt
    aboutQtAction = new QAction("&About Qt", this);
    connect(aboutQtAction, SIGNAL(triggered()), this, SLOT(QApplication::aboutQt()));
}

void FenPrincipale::createMenus()
{
    //File Tab
    QMenu *fileMenu = menuBar()->addMenu("&File");

    fileMenu->addAction(newTabAction);
    fileMenu->addAction(closeTabAction);
    fileMenu->addSeparator();
    fileMenu->addAction(closeProgramAction);

    //Navigation Tab
    QMenu *navigationMenu = menuBar()->addMenu("&Navigation");

    navigationMenu->addAction(previousPageAction);
    navigationMenu->addAction(nextPageAction);
    navigationMenu->addAction(stopRefreshAction);
    navigationMenu->addAction(refreshPageAction);
    navigationMenu->addAction(homePageAction);

    //About Tab
    QMenu *aboutMenu = menuBar()->addMenu("&?");

    aboutMenu->addAction(aboutNavigoAction);
    aboutMenu->addAction(aboutQtAction);
}

void FenPrincipale::createToolsBar()
{
    //Create a web address text field
    webAddress = new QLineEdit();
    connect (webAddress, SIGNAL(returnPressed()), this, SLOT(loadPage()));

    //Create toolsbar
    QToolBar *toolBar = addToolBar("Navigation");

    toolBar->addAction(previousPageAction);
    toolBar->addAction(nextPageAction);
    toolBar->addAction(stopRefreshAction);
    toolBar->addAction(refreshPageAction);
    toolBar->addAction(homePageAction);
    toolBar->addWidget(webAddress);
    toolBar->addAction(goAction);
}

void FenPrincipale::createProgressBar()
{
    progressBar = new QProgressBar(this);
    progressBar->setVisible(false);
    progressBar->setMaximumHeight(14);
    statusBar()->addWidget(progressBar,1);
}

/***********Slots***********/

void FenPrincipale::previousPage()
{
    currentPage()->back();
}

void FenPrincipale::nextPage()
{
    currentPage()->forward();
}

void FenPrincipale::stopRefresh()
{
    currentPage()->stop();
}

void FenPrincipale::refreshPage()
{
    currentPage()->reload();
}

void FenPrincipale::homePage()
{
    currentPage()->load(QUrl("http://www.google.com/"));
}

void FenPrincipale::aboutAzizNavigo()
{
    QMessageBox::information(this, "About Aziz Navigo", "Aziz Navigo is a basic navigator created by Mohamed Aziz Tousli.");
}

void FenPrincipale::newTab()
{
    int indexNewTab = tabs->addTab(createTabWebPage("www.google.com"), "New Page");
    tabs->setCurrentIndex(indexNewTab);
    webAddress->setText("");
    webAddress->setFocus(Qt::OtherFocusReason);
}

void FenPrincipale::closeTab()
{
    if (tabs->count()!=0)
        tabs->removeTab(tabs->currentIndex());
    else
        QMessageBox::critical(this, "Error!", "One tab must be open!");
}

void FenPrincipale::loadPage()
{
    QString url = webAddress->text();

    // Add "http://" to address
    if (url.left(7) != "http://")
    {
        url = "http://" + url;
        webAddress->setText(url);
    }

    currentPage()->load(QUrl(url));

}

void FenPrincipale::tabChanged(int index)
{
    changeTitle(currentPage()->title());
    changeUrl(currentPage()->url());
}

/***********Slots Relate to Web Page***********/

void FenPrincipale::changeTitle(QString longTitle)
{
    QString shortTitle = longTitle;

    //To avoid large titles
    if (longTitle.size() > 40)
        shortTitle = longTitle.left(40) + "...";

    setWindowTitle(shortTitle + " - " + "Aziz Navigo");
    tabs->setTabText(tabs->currentIndex(), shortTitle);
}

void FenPrincipale::changeUrl(QUrl url)
{
    if (url.toString() != "html/page_blanche.html")
        webAddress->setText(url.toString());
}

void FenPrincipale::loadStarted()
{
    progressBar->setVisible(true);
}

void FenPrincipale::loadProgress(int val)
{
    progressBar->setValue(val);
}

void FenPrincipale::loadFinished(bool end)
{
    progressBar->setVisible(false);
    statusBar()->showMessage("Ready", 2000);
}

/***********Other Methods***********/

QWidget *FenPrincipale::createTabWebPage(QString url)
{
    QWidget *webWidget = new QWidget; //Create widget
    QWebView *webPage = new QWebView(); //Create page
    webPage->load(QUrl(url)); //Load page with url

    //Add page to widget
    QVBoxLayout *layout = new QVBoxLayout;
    layout->setContentsMargins(0,0,0,0);
    layout->addWidget(webPage);
    webWidget->setLayout(layout);

    if (url.isEmpty())
        {
            webPage->load(QUrl(tr("html/blankPage.html")));
        }
        else
        {
            if (url.left(7) != "http://")
            {
                url = "http://" + url;
            }
            webPage->load(QUrl(url));
        }

    //Deal with signals sent by webPage
    connect(webPage, SIGNAL(titleChanged(QString)), this, SLOT(changeTitle(QString)));
    connect(webPage, SIGNAL(urlChanged(QUrl)), this, SLOT(changeUrl(QUrl)));
    connect(webPage, SIGNAL(loadStarted()), this, SLOT(loadStarted()));
    connect(webPage, SIGNAL(loadProgress(int)), this, SLOT(loadProgress(int)));
    connect(webPage, SIGNAL(loadFinished(bool)), this, SLOT(loadFinished(bool)));

    return webWidget;
}

QWebView *FenPrincipale::currentPage()
{
    return tabs->currentWidget()->findChild<QWebView *>();
}
