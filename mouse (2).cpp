#define _WIN32_WINNT 0x0400
#pragma comment( lib, "user32.lib" )

#include <windows.h>
#include <stdio.h>
#include<iostream>
#include <fstream>
#include <ctime>

using namespace std;

HHOOK hKeyBoardHook = NULL;
HHOOK hMouseHook = NULL;
std::ofstream outfile;
clock_t startTime = clock();
int keyPressCount = 0;


double duration;
__declspec(dllexport) LRESULT CALLBACK KeyboardEvent (int nCode, WPARAM wParam, LPARAM lParam)
{
    MOUSEHOOKSTRUCT * pMouseStruct = (MOUSEHOOKSTRUCT *)lParam;
     static int numClicksLeft = 0;
     static int numClicksRight = 0;

     if (pMouseStruct != NULL){
        if(wParam == WM_LBUTTONDOWN)
        {
            numClicksLeft++;
            //mouseX = pMouseStruct->pt.x;
            //mouseY = pMouseStruct->pt.y;

            //cout << mouseX;
            //cout << mouseY;
            //printf("MouseClicks: %d\n",numClicks);
        }
        if(wParam == WM_RBUTTONDOWN)
        {
            numClicksRight++;
        }
        duration = ((clock()-startTime)/(double) CLOCKS_PER_SEC);
        if(duration>=5.0){
                outfile<<duration<<" "<<clock()<<" "<<startTime<<" "<<CLOCKS_PER_SEC<<endl;
                outfile<<"leftClick "<<numClicksLeft<<endl;
                outfile<<"rightClick "<<numClicksRight<<endl;
                outfile<<"keyPress "<<keyPressCount<<endl;

                outfile<<"Clicks Per Sec "<<(double)((numClicksLeft+numClicksRight)/duration)<<endl<<endl<<endl;


                outfile.flush();
                numClicksLeft=0;
                numClicksRight=0;
                startTime = clock();
                duration = 0;
                }
     }

    //if (pMouseStruct != NULL)
      //  printf("Mouse position X = %d  Mouse Position Y = %d\n", pMouseStruct->pt.x,pMouseStruct->pt.y);
    return CallNextHookEx(hMouseHook,
        nCode,wParam,lParam);
}

LRESULT CALLBACK KeyboardTracker(int code,WPARAM wParam,LPARAM lParam)
{
    cout<<"@@@"<<endl;
    if(code==HC_ACTION)
    {
        keyPressCount++;
        cout<<keyPressCount<<"#"<<endl;
    }
    return ::CallNextHookEx(hKeyBoardHook,code,wParam,lParam);
}

void MessageLoop()
{
    MSG message;
    while (GetMessage(&message,NULL,0,0)) {
        TranslateMessage( &message );
        DispatchMessage( &message );
    }
}

DWORD WINAPI MyMouseLogger(LPVOID lpParm)
{
    HINSTANCE hInstance = GetModuleHandle(NULL);
    if (!hInstance) hInstance = LoadLibrary((LPCSTR) lpParm);
    if (!hInstance) return 1;

    hMouseHook = SetWindowsHookEx (
        WH_MOUSE_LL,
        (HOOKPROC) KeyboardEvent,
        hInstance,
        NULL
        );
    hKeyBoardHook = SetWindowsHookEx(WH_KEYBOARD,
                                     (HOOKPROC) KeyboardTracker,hInstance,NULL);
    MessageLoop();
    UnhookWindowsHookEx(hMouseHook);
    UnhookWindowsHookEx(hKeyBoardHook);
    return 0;
}

int main(int argc, char** argv)
{


  outfile.open("test.txt", std::ios_base::app);
    //freopen ("mouse_click.txt","w",stdout);
    HANDLE hThread;
    DWORD dwThread;

    hThread = CreateThread(NULL,NULL,(LPTHREAD_START_ROUTINE)
        MyMouseLogger, (LPVOID) argv[0], NULL, &dwThread);

        //fclose (stdout);
        //fclose (pFileTXT);

    if (hThread)
        return WaitForSingleObject(hThread,INFINITE);
    else return 1;

}
