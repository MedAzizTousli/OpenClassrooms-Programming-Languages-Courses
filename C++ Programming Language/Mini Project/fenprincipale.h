#ifndef FENPRINCIPALE_H
#define FENPRINCIPALE_H

#include <QtWidgets>
#include <QApplication>
#include <QWebView>

class FenPrincipale: public QMainWindow
{
    Q_OBJECT

public:
    FenPrincipale();
    QWebView *currentPage();

private:
    void createActions();
    void createMenus();
    void createToolsBar();
    void createProgressBar();
    QWidget *createTabWebPage(QString url);


private slots:
    //General slots
    void previousPage();
    void nextPage();
    void refreshPage();
    void stopRefresh();
    void homePage();
    //Specific slots
    void aboutAzizNavigo();
    void newTab();
    void closeTab();
    void loadPage();
    void tabChanged(int index);
    //WebPage slots
    void changeTitle(QString title);
    void changeUrl(QUrl url);
    void loadStarted();
    void loadProgress(int val);
    void loadFinished(bool end);

private:
    QTabWidget *tabs;

    QAction *newTabAction;
    QAction *closeTabAction;
    QAction *closeProgramAction;
    QAction *previousPageAction;
    QAction *nextPageAction;
    QAction *stopRefreshAction;
    QAction *refreshPageAction;
    QAction *homePageAction;
    QAction *aboutNavigoAction;
    QAction *aboutQtAction;
    QAction *goAction;

    QLineEdit *webAddress;
    QProgressBar *progressBar;
};

#endif // FENPRINCIPALE_H
