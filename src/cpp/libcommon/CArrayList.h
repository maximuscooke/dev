#ifndef _CARRAYLIST_H_
#define _CARRAYLIST_H_

#include "CCollection.h"
#include <vector>
#include <functional>
#include <random>
#include <chrono>

namespace  _COMMON_NS
{
    template <class T> 
    class CArrayList : public CCollection
    {
        private:
            std::vector<T> mVector;

        public:
            std::function<std::string (T)> mToStringFunc;
 
            CArrayList( UInt initialSize = _DEFAULT_COLLECTION_SIZE, std::function<std::string (T)> toStringfunc = NULL)
            {
                reserve(initialSize);

                mToStringFunc = toStringfunc;
           }
          
            _INLINE T operator[](Index index) { return this->mVector[index]; }

            void log() const _OVERRIDE;
            void clear() _OVERRIDE;
            Boolean isEmpty() const _OVERRIDE;
            std::string getClassName() const _OVERRIDE;
            std::string toString() const _OVERRIDE;
            Count getElementCount() const _OVERRIDE { return getCount(); };
            
            std::string toString(std::function<std::string (T)> lfnc) const;
            _INLINE std::string toString(T t, std::function<std::string (T)>) const;

            _INLINE void add(T t); 
            _INLINE T    getFirst() const;
            _INLINE T    getLast() const;
            _INLINE T    getAt(Index index) const;
            _INLINE void setAt(Index index, T t);
            _INLINE void insertAt(Index index, T t);
            _INLINE void removeAt(Index index);
            _INLINE void swapElements(Index a, Index b);
            _INLINE void reserve(UInt sz);
            _INLINE void resize(Count sz);
            _INLINE void shrink();
            _INLINE void popBack();
            _INLINE void pushBack(T& t);
            _INLINE void swap(std::vector<T>& v);
            _INLINE void reverse();
            _INLINE void shuffle();
 
            void sort(int order = _ASCENDING, std::function<Integer (T, T)> f = [](T a, T b) { return a > b; });
            std::vector<T *> search(std::function<Bool (T&)> f);
            
            _INLINE Count getCount() const;
            _INLINE Count getCapacity() const;

            _INLINE void setToStringFunc(std::function<std::string (T)> f) { this->mToStringFunc = f; }
     };
 
    template<class T> void CArrayList<T>::log() const 
    {
        std::cout << "Class <" << this->getClassName() << "> Count := " << getCount() << ", Capacity := " << getCapacity();
    }

    template<class T>  std::string CArrayList<T>::toString(T t, std::function<std::string (T)> lfnc) const
    {
        return lfnc(t);
    }

    template<class T> std::string CArrayList<T>::toString(std::function<std::string (T)> lfnc) const
    {
        std::string str("{ ");

        Count numElements = this->getCount();

        Count i = 0;

        if (numElements == 0)
        {
            str.append(" list is empty ");
        }
        else if (numElements == 1)
        {
            str.append( this->toString( this->getAt(0), lfnc ) );
        }
        else
        {
            for(i = 0; i < numElements - 1; i++)
            {
                str.append( this->toString( this->getAt(i), lfnc ) );

                str.append(", ");
            }
            str.append( this->toString( this->getAt(i), lfnc ) );
        }

        str.append(" }");

        return str;
    }

    template<class T> std::string CArrayList<T>::toString() const 
    {
        if (this->mToStringFunc == NULL)
        {
            char buffer[128];

            sprintf(buffer, " contains %d element(s)", this->getCount());

            return this->getClassName().append(buffer);
        }
        return this->toString(this->mToStringFunc);
     }

    template<class T> std::string CArrayList<T>::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CArrayList) ); 
    }

    template<class T> std::vector<T *> CArrayList<T>::search(std::function<Bool (T&)> f)
    {
        std::vector<T *> results;

         for(Int i = 0; i < this->getCount(); i++)
         {
             if ( f( this->mVector[i] ) )
             {
                results.push_back( &this->mVector[i] );
             }
         }

         return results;
    }

    template<class T> void CArrayList<T>::sort(int order, std::function<Integer (T, T)> f)
    {
        std::sort(std::begin( this->mVector ), std::end( this->mVector), f );

        if (order == _ASCENDING)
        {
            this->reverse();
        }
    }

    template<class T> void CArrayList<T>::swapElements(Index a, Index b)
    {
        T tmp = this->mVector[b];

        this->mVector[b] = this->mVector[a];

        this->mVector[a] = tmp;
    }

    template<class T> void CArrayList<T>::shuffle()
    {
        unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();

        std::shuffle(std::begin(this->mVector), std::end(this->mVector), std::default_random_engine(seed));
    }

    template<class T> void CArrayList<T>::reverse()
    {
        std::reverse(std::begin(this->mVector), std::end(this->mVector));
    }

    template<class T> void CArrayList<T>::swap(std::vector<T>& v)
    {
        this->mVector.swap(v);
    }

    template<class T> void CArrayList<T>::pushBack(T& t)
    {
        this->mVector.push_back(t);
    }

    template<class T> void CArrayList<T>::popBack()
    {
        this->mVector.pop_back();
    }

    template<class T> void CArrayList<T>::setAt(Index index, T t)
    {
        return this->mVecto[index] = t;
    }

    template<class T> void CArrayList<T>::resize(Count sz)
    {
        return this->mVector.resize();
    }

    template<class T> void CArrayList<T>::shrink()
    {
        return this->mVector.shrink_to_fit();
    }

    template<class T> T CArrayList<T>::getFirst() const
    {
        return this->mVector.front();
    }

    template<class T> T CArrayList<T>::getLast() const
    {
        return this->mVector.back();
    }

    template<class T> void CArrayList<T>::insertAt(Index index, T t)
    {
        this->mVector.insert(this->mVector.begin() + index,  t);
    }

    template<class T> void CArrayList<T>::removeAt(Index index)
    {
        this->mVector.erase(index);
    }

    template<class T> T CArrayList<T>::getAt(Index index) const
    {
        return this->mVector.at(index);
    }

    template<class T> void CArrayList<T>::add(T t) 
    { 
        this->mVector.emplace_back(t);
    }

    template<class T> void CArrayList<T>::reserve(UInt sz) 
    { 
        this->mVector.reserve(sz); 
    }

    template<class T> Count CArrayList<T>::getCapacity() const
    { 
        return (Count)this->mVector.capacity(); 
    }
    
    template<class T> Boolean CArrayList<T>::isEmpty() const 
    { 
        return (Boolean)this->mVector.empty(); 
    }

    template<class T> Count CArrayList<T>::getCount() const 
    { 
        return (Count)mVector.size(); 
    } 

    template<class T> void CArrayList<T>::clear() 
    { 
        this->mVector.clear();
    } 
}
#endif
