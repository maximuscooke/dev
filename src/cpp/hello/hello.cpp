#include <stdio.h>
#include <iostream>
#include <typeinfo>
#include "Common.h"

using namespace _COMMON_NS;

#include <algorithm>
#include <vector>
#include <string>
#include "CMemBlock.h"

using namespace std;

   int main()
    {
        CMemBlock<Double> mem(10, _MB_AUTO_DELETE | _MB_AUTO_INIT );

        mem.init();

        mem.setAt(0, 99.77);
        mem.setAt(1, 33.33);
        mem.setAt(2, 234.44);
        mem.setAt(3, 12.2);
        mem.setAt(4, 3.55);

        for(Index i = 0; i < 7; i++)
        {
            std::cout << mem.getAt(i) << std::endl;
        }

        std::cout << mem.sizeInBytes() << std::endl;

        mem.resize(50);

        std::cout << mem.sizeInBytes() << std::endl;

        for(Index i = 0; i < 7; i++)
        {
            std::cout << mem.getAt(i) << std::endl;
        }

        return 0;
    }


// class MyThread : public CThread, public CThreadCallBack
// {
//     public:
//         _OVERRIDE void runAsync()
//         {
//             sleepFor((std::chrono::seconds)5);

//             std::cout << "MyThread running. " << std::endl;
//         }

//         _OVERRIDE void threadStarting(int id)
//         {
//             std::cout << "thread Starting " << id << std::endl;
//         }

//         _OVERRIDE void threadStopped(int id)
//         {
//             std::cout << "thread Stopped " << id << std::endl;
//         }

//         _OVERRIDE void threadFinished(int id)
//         {
//             std::cout << "thread Finished "  << id << std::endl;
//         }

//     };

 
// int main()
// {   
//     MyThread t;

//     std::cout << "Starting." << std::endl;

//     t.start(1, &t);

//     t.wait();

//      return 0;
// }
