#include <QApplication>
#include "fenprincipale.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    FenPrincipale window;

    window.show();

    return app.exec();
}
