package it.unibo.inner;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithoutPolicy<T> implements IterableWithPolicy<T>{
    private T[] elements;
    private Predicate<T> filter;

    public IterableWithoutPolicy(T[] elements){
        this(elements,t->true);
    }

    public IterableWithoutPolicy(T[] elements,Predicate<T> filter){
        this.elements=elements;
        this.filter=filter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter=filter;
    }
    
    public class CustomIterator implements Iterator<T>{
        private int currentPosition=0;

        @Override
        public boolean hasNext() {
            while(currentPosition < elements.length){
                if(filter.test(elements[currentPosition])){
                    return true;
                }
                currentPosition++;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext()){
                return elements[currentPosition++];
            }
            throw new java.util.NoSuchElementException();
        }
        
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }
}
