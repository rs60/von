#define _WIN32_WINNT 0x0400
#pragma comment( lib, "user32.lib" )

#include <windows.h>
#include <stdio.h>
#include<iostream>

HHOOK hMouseHook;

__declspec(dllexport) LRESULT CALLBACK KeyboardEvent (int nCode, WPARAM wParam, LPARAM lParam)
{
    MOUSEHOOKSTRUCT * pMouseStruct = (MOUSEHOOKSTRUCT *)lParam;
     static int numClicks = 0;
     if (pMouseStruct != NULL)
        if(wParam == WM_LBUTTONDOWN)
        {
            numClicks++;
            //mouseX = pMouseStruct->pt.x;
            //mouseY = pMouseStruct->pt.y;

            //cout << mouseX;
            //cout << mouseY;
            printf("MouseClicks: %d\n",numClicks);

        }
    //if (pMouseStruct != NULL)
      //  printf("Mouse position X = %d  Mouse Position Y = %d\n", pMouseStruct->pt.x,pMouseStruct->pt.y);
    return CallNextHookEx(hMouseHook,
        nCode,wParam,lParam);
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
    MessageLoop();
    UnhookWindowsHookEx(hMouseHook);
    return 0;
}

int main(int argc, char** argv)
{
    //freopen ("mouse_click.txt","w",stdout);
    HANDLE hThread;
    DWORD dwThread;

    hThread = CreateThread(NULL,NULL,(LPTHREAD_START_ROUTINE)
        MyMouseLogger, (LPVOID) argv[0], NULL, &dwThread);

        //fclose (stdout);

    if (hThread)
        return WaitForSingleObject(hThread,INFINITE);
    else return 1;

}
